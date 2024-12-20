package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

/**
 * La classe StartGameState rappresenta lo stato iniziale del gioco, in cui vengono distribuite le carte 
 * e viene impostato il primo giocatore. Implementa l'interfaccia {@link GameState}.
 */
@Component
public class StartGameState implements GameState {

    @Autowired
    private PartitaService partitaService;

    /**
     * Esegue la logica dello stato iniziale del gioco.
     * 
     * @param partita l'oggetto {@link Partita} su cui eseguire lo stato.
     */
    @Override
    public void execute(Partita partita) {
        // Distribuisce le carte iniziali ai giocatori
        partitaService.distribuisciCarteIniziali(partita);
        
        // Imposta il primo giocatore come quello corrente
        partita.setIndiceGiocatoreCorrente(0);
        
        // Passa allo stato di turno per iniziare il gioco
        partitaService.setGameState(partita, new TurnState());
        
        System.out.println("Partita iniziata! Turno del primo giocatore.");
    }
}
