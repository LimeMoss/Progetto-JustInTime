package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

@Component
public class StartGameState implements GameState {
    
    @Autowired
    private PartitaService partitaService;

    /**
     * Esegue le operazioni necessarie all'inizio di una partita.
     * Distribuisce le carte iniziali ai giocatori, imposta il primo giocatore
     * corrente e passa allo stato di turno.
     * 
     * @param partita La partita su cui eseguire le operazioni.
     */
    
    @Override
    public void execute(Partita partita) {
    
        
        partitaService.distribuisciCarteIniziali(partita);
        partita.setIndiceGiocatoreCorrente(0);
        
        partitaService.setGameState(partita, new TurnState());
    
    }
}