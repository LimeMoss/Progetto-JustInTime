package com.justInTime.service;

import com.justInTime.model.*;
import com.justInTime.repository.PartitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartitaService {

    @Autowired
    private PartitaRepository partitaRepository;

    public Partita createPartita() {
        Partita partita = new Partita();
        return partitaRepository.save(partita);
    }

    public Partita getPartita(Long partitaId) {
        return partitaRepository.findById(partitaId)
            .orElseThrow(() -> new RuntimeException("Partita non trovata"));
    }

    public Partita aggiungiGiocatore(Long partitaId, Player giocatore) {
        Partita partita = getPartita(partitaId);
        if (partita.getGiocatori().size() < 4 && !partita.getGiocatori().contains(giocatore)) {
            partita.getGiocatori().add(giocatore);
            giocatore.getPartite().add(partita);
            return partitaRepository.save(partita);
        }
        throw new RuntimeException("Impossibile aggiungere il giocatore");
    }

    public void rimuoviGiocatore(Long partitaId, Long giocatoreId) {
        Partita partita = getPartita(partitaId);
        partita.getGiocatori().removeIf(g -> g.getId().equals(giocatoreId));
        partitaRepository.save(partita);
    }

    public Partita iniziaPartita(Long partitaId) {
        Partita partita = getPartita(partitaId);
        if (partita.getGiocatori().size() < 2) {
            throw new RuntimeException("Numero insufficiente di giocatori");
        }
        distribuisciCarteIniziali(partita);
        partita.setGameState(new StartGameState());
        return partitaRepository.save(partita);
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

    private void distribuisciCarteIniziali(Partita partita) {
        for (Player giocatore : partita.getGiocatori()) {
            for (int i = 0; i < 5; i++) {
                giocatore.aggiungiCartaAllaMano(partita.getMazzoNormale().pescaCarta());
            }
        }
    }

    private void passaAlProssimoGiocatore(Partita partita) {
        partita.setIndiceGiocatoreCorrente(
            (partita.getIndiceGiocatoreCorrente() + 1) % partita.getGiocatori().size()
        );
    }

    public void terminaPartita(Long partitaId) {
        Partita partita = getPartita(partitaId);
        partita.setGameState(new EndGameState());
        partitaRepository.save(partita);
    }
}