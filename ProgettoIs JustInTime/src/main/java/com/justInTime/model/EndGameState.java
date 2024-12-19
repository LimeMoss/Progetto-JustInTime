package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.justInTime.service.PartitaService;

public class EndGameState implements GameState {

    @Autowired
    private PartitaService partitaService;

    @Override
    public void execute(Partita partita) {
        partitaService.terminaPartita(partita);  // Termina la partita delegando al PartitaService
        
    }
}
