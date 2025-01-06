package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

@Component
public class StartGameState implements GameState {

    @Autowired
    private ApplicationContext applicationContext;
    
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
    
        
        partitaService.distribuisciCarteIniziali(partita.getId());
        partita.setIndiceGiocatoreCorrente(0);

        TurnState turnState = applicationContext.getBean(TurnState.class);
        partitaService.setsGameState(partita.getId(), turnState);
        
    }
}