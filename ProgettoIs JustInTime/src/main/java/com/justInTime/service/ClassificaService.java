package com.justInTime.service;

import com.justInTime.model.Player;
import com.justInTime.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
