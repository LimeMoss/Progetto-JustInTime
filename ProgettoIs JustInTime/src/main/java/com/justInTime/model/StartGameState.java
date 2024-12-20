package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

@Component
public class StartGameState implements GameState {
    
    @Autowired
    private PartitaService partitaService;

    @Override
    public void execute(Partita partita) {
        // Distribuiamo le carte iniziali
        partitaService.distribuisciCarteIniziali(partita);
        
        // Impostiamo il primo giocatore
        partita.setIndiceGiocatoreCorrente(0);
        
        // Passiamo allo stato di turno
        partitaService.setGameState(partita, new TurnState());
        
        System.out.println("Partita iniziata! Turno del primo giocatore.");
    }
}