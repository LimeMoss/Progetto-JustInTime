package com.justInTime.service;

import com.justInTime.model.Player;
import com.justInTime.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificaService {

    private final UtenzaRepository utenzaRepository;

    public ClassificaService(UtenzaRepository utenzaRepository) {
        this.utenzaRepository = utenzaRepository;
    }

    /**
     * Restituisce i giocatori ordinati in base al loro punteggio massimo (maxScore) in ordine decrescente.
     *
     * @return Lista di giocatori ordinata per punteggio.
     */
    public List<Player> getClassifica() {
        return utenzaRepository.findAllByOrderByMaxScoreDesc();
    }
}
