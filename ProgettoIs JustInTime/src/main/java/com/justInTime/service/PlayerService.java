package com.justInTime.service;

import com.justInTime.model.Carta;
import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.model.Utenza;
import com.justInTime.repository.PartitaRepository;
import com.justInTime.repository.PlayerRepository;
import com.justInTime.repository.UtenzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PartitaRepository partitaRepository;
    private final UtenzaRepository utenzaRepository;

    public PlayerService(PlayerRepository playerRepository, PartitaRepository partitaRepository, UtenzaRepository utenzaRepository) {
        this.playerRepository = playerRepository;
        this.partitaRepository = partitaRepository;
        this.utenzaRepository = utenzaRepository;
    }

    // Crea un nuovo giocatore
    @Transactional
    public Player creaGiocatore(String name, int maxScore, Long utenzaId) {
        Utenza utenza = utenzaRepository.findById(utenzaId).orElseThrow(() -> new RuntimeException("Utenza non trovata."));

        Player player = new Player(name, maxScore);
        player.associaUtenza(utenza); // Associa il paese dell'utenza al giocatore
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
        player.setNome(nuovoNome);
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
        player.increaseMaxScore();
        return playerRepository.save(player);
    }

    // Elimina un giocatore
    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player non trovato"));

        for (Partita partita : player.getPartite()) {
            partita.getGiocatori().remove(player);
            partitaRepository.save(partita);
        }

        playerRepository.deleteById(id);
    }
}
