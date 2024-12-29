package com.justInTime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justInTime.model.Carta;
import com.justInTime.model.EndGameState;
import com.justInTime.model.GameState;
import com.justInTime.model.MazzoScarto;
import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.model.StartGameState;
import com.justInTime.repository.PartitaRepository;
import com.justInTime.repository.PlayerRepository;

@Service
public class PartitaService {

    @Autowired
    private PartitaRepository partitaRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    public void iniziaPartita(Long partitaId) {
        Partita partita = getPartita(partitaId);
        if (partita.getGiocatori().size() < 2) {
            throw new RuntimeException("Numero insufficiente di giocatori");
        }
        distribuisciCarteIniziali(partita);
        partita.setGameState(new StartGameState());
    }
    
    public Partita getPartita(Long partitaId) {
        return partitaRepository.findById(partitaId)
            .orElseThrow(() -> new RuntimeException("Partita non trovata"));
    }


    public Partita giocaCarta(Long partitaId, int cartaIndex) {
        Partita partita = getPartita(partitaId);
        Player giocatoreCorrente = partita.getGiocatoreCorrente();
        Carta carta = giocatoreCorrente.getMano().get(cartaIndex);
        
        if (cartaGiocabile(partita, carta)) {
            if (carta.getValore() == 99) {
                carta.applicaEffetto(giocatoreCorrente);
            }
            giocatoreCorrente.getMano().remove(cartaIndex);
            partita.getMazzoScarto().aggiungi(carta);
            passaAlProssimoGiocatore(partita);
            return partitaRepository.save(partita);
        }
        throw new RuntimeException("Carta non giocabile");
    }

    private boolean cartaGiocabile(Partita partita, Carta carta) {
        int specialValue = 99;
        MazzoScarto mazzoScarto = partita.getMazzoScarto();
        if (mazzoScarto != null) {
            int value = mazzoScarto.ultimaCartaScartata().getValore();
            return carta.getValore() == value + 1 || 
                   carta.getValore() == value - 1 || 
                   carta.getValore() == specialValue;
        }
        throw new RuntimeException("Il mazzo non è un mazzo di scarto");
    }

    public void distribuisciCarteIniziali(Partita partita) {
        for (Player giocatore : partita.getGiocatori()) {
            for (int i = 0; i < 5; i++) {
                giocatore.aggiungiCartaAllaMano(partita.getMazzoNormale().pescaCarta());
            }
        }
    }

    public void passaAlProssimoGiocatore(Partita partita) {
        partita.setIndiceGiocatoreCorrente(
            (partita.getIndiceGiocatoreCorrente() + 1) % partita.getGiocatori().size()
        );
    }

    public void terminaPartita(Long partitaId) {
        Partita partita = getPartita(partitaId);
        partita.setGameState(new EndGameState());
        partitaRepository.save(partita);
    }

    public void setGameState(Partita partita, GameState gamestate){

            partita.setGameState(gamestate);

    }
        @Transactional
    public Player aggiungiGiocatoreAPartita(Long playerId, Long partitaId) {
        Player player = playerService.trovaGiocatore(playerId);
        Partita partita = partitaRepository.findById(partitaId)
                .orElseThrow(() -> new RuntimeException("Partita non trovata."));
        
        // Verifica se il giocatore è già nella partita
        if (!partita.getGiocatori().contains(player)) {
            partita.getGiocatori().add(player);
            player.getPartite().add(partita);
            partitaRepository.save(partita);
        }
        
        return playerRepository.save(player);
    }

    @Transactional
    public Player rimuoviGiocatoreDaPartita(Long playerId, Long partitaId) {
        Player player = playerService.trovaGiocatore(playerId);
        Partita partita = partitaRepository.findById(partitaId)
                .orElseThrow(() -> new RuntimeException("Partita non trovata."));
        
        partita.getGiocatori().remove(player);
        player.getPartite().remove(partita);
        partitaRepository.save(partita);
        
        return playerRepository.save(player);
    }
    @Transactional
    public boolean isGiocatoreInPartita(Long playerId, Long partitaId) {
        Player player = playerService.trovaGiocatore(playerId);
        return player.getPartite().stream()
                .anyMatch(partita -> partita.getId().equals(partitaId));
    }
}