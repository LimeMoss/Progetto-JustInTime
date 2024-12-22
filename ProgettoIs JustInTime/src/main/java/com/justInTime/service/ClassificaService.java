package com.justInTime.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.justInTime.model.Utenza;
import com.justInTime.repository.UtenzaRepository;

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
    public List<Utenza> getClassifica() {
        return utenzaRepository.findAllByOrderByMaxScoreDesc();
    }
}
