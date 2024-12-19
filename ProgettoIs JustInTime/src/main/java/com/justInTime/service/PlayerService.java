package com.justInTime.service;

import com.justInTime.model.Carta;
import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.repository.PartitaRepository;
import com.justInTime.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final PartitaRepository partitaRepository;

    public PlayerService(PlayerRepository playerRepository, PartitaRepository partitaRepository) {
        this.playerRepository = playerRepository;
        this.partitaRepository = partitaRepository;
    }

    // Crea un nuovo giocatore
    public Player creaGiocatore(String name, int maxScore) {
        Player player = new Player(name, maxScore);
        return playerRepository.save(player);
    }

    // Trova un giocatore per ID
    public Player trovaGiocatore(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Giocatore non trovato."));
    }

    // Ottieni tutti i giocatori
    public List<Player> trovaTuttiGiocatori() {
        return playerRepository.findAll();
    }

    // Aggiorna il nome del giocatore
    @Transactional
    public Player aggiornaNomeGiocatore(Long playerId, String nuovoNome) {
        Player player = trovaGiocatore(playerId);
        player.setName(nuovoNome);
        return playerRepository.save(player);
    }

    // Aggiungi una carta alla mano del giocatore
    @Transactional
    public Player aggiungiCartaAllaMano(Long playerId, Carta carta) {
        Player player = trovaGiocatore(playerId);
        player.aggiungiCartaAllaMano(carta);
        return playerRepository.save(player);
    }

    // Rimuovi una carta dalla mano del giocatore
    @Transactional
    public Player rimuoviCartaDallaMano(Long playerId, Carta carta) {
        Player player = trovaGiocatore(playerId);
        player.rimuoviCartaDallaMano(carta);
        return playerRepository.save(player);
    }

    // Incrementa il punteggio massimo del giocatore
    @Transactional
    public Player incrementaMaxScore(Long playerId) {
        Player player = trovaGiocatore(playerId);
        player.IncreaseMaxScore();
        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player non trovato"));

        for (Partita partita : player.getPartite()) {
            partita.getGiocatori().remove(player);
            partitaRepository.save(partita);
        }


        playerRepository.deleteById(id);
    }
}