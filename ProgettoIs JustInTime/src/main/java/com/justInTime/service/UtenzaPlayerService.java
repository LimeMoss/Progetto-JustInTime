package com.justInTime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justInTime.model.Utente;

import com.justInTime.repository.UtenzaRepository;

//CLASSE NECESSARIA PER EVITARE  DIPENDENZE CIRCOLARI//
@Service
public class UtenzaPlayerService {


    @Autowired
    private UtenzaRepository utenzaRepository;
    

    public Utente trovaUtente(Long id) {
        return utenzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovata."));
    }
}
