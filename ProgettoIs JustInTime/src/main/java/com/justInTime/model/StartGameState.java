package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

@Component("startGameState")
public class StartGameState implements GameState {

    @Autowired
    private  PartitaService partitaService;

       @Autowired
   
    @Qualifier("turnState")
    private GameState turnState;
    

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
        partitaService.setsGameState(partita.getId(), turnState);
    }
}
