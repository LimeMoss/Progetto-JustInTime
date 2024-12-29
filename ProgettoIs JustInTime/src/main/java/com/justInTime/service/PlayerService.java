package com.justInTime.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.justInTime.model.*;
import com.justInTime.repository.*;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final UtenzaRepository utenzaRepository;

    public PlayerService(PlayerRepository playerRepository, PartitaRepository partitaRepository, 
                        UtenzaRepository utenzaRepository) {
        this.playerRepository = playerRepository;
        this.utenzaRepository = utenzaRepository;
    }

    @Transactional
    public Player creaGiocatore(String name, Long utenzaId) {
        Utente utenza = utenzaRepository.findById(utenzaId)
            .orElseThrow(() -> new RuntimeException("Utenza non trovata."));
        Player player = new Player(name);
        player.setUtente(utenza);  
        return playerRepository.save(player);
    }

    public Player trovaGiocatore(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Giocatore non trovato."));
    }

    public List<Player> trovaTuttiGiocatori() {
        return playerRepository.findAll();
    }


    @Transactional
    public Player aggiungiCartaAllaMano(Long playerId, Carta carta) {
        Player player = trovaGiocatore(playerId);
        player.aggiungiCartaAllaMano(carta);
        return playerRepository.save(player);
    }

    @Transactional
    public Player rimuoviCartaDallaMano(Long playerId, Carta carta) {
        Player player = trovaGiocatore(playerId);
        player.rimuoviCartaDallaMano(carta);
        return playerRepository.save(player);
    }

    @Transactional
    public Player incrementaMaxScore(Long playerId) {
        Player player = trovaGiocatore(playerId);
        player.increaseMaxScore();
        return playerRepository.save(player);
    }

    

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }


    @Transactional
    public List<Partita> getPartiteGiocatore(Long playerId) {
        Player player = trovaGiocatore(playerId);
        return player.getPartite();
    }

}