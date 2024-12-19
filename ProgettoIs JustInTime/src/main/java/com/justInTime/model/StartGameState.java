package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.justInTime.service.PartitaService;

public class StartGameState implements GameState {
    
    @Autowired
    private PartitaService partitaService;

    @Override
    public void execute(Partita partita) {
        partitaService.distribuisciCarteIniziali(partita);  // Distribuiamo le carte iniziali
        partitaService.setGameState(partita, new TurnState());  // Impostiamo lo stato a TurnState
    }
}