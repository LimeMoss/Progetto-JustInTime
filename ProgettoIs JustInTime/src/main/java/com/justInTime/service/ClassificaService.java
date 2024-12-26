package com.justInTime.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.justInTime.model.Player;
import com.justInTime.repository.PlayerRepository;


@Service
public class ClassificaService {

    private final PlayerRepository playerRepository;

    public ClassificaService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Restituisce i giocatori ordinati in base al loro punteggio massimo (maxScore) in ordine decrescente.
     *
     * @return Lista di giocatori ordinata per punteggio.
     */
    public List<Player> getClassifica() {
        return playerRepository.findAllByOrderByMaxScoreDesc();
    }
}
