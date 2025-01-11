package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

@Component("startGameState")
public class StartGameState implements GameState {

    @Autowired
    private PartitaService partitaService;

    @Autowired
    @Qualifier("turnState")
    private GameState turnState;

    private volatile boolean PlayerReady = false;
    private static final long TIMEOUT = 50000;

    private Partita tpartita = new Partita();
    /**
     * Esegue le operazioni necessarie all'inizio di una partita.
     * Distribuisce le carte iniziali ai giocatori, imposta il primo giocatore
     * corrente e passa allo stato di turno.
     * 
     * @param partita La partita su cui eseguire le operazioni.
     */
    @Override
    public void execute(Partita partita) {
        tpartita = partita;
        long startTime = System.currentTimeMillis();
    
    
        partitaService.distribuisciCarteIniziali(partita);
        partita.setIndiceGiocatoreCorrente(0);
    
        while (!PlayerReady) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    
            /* 
            if (System.currentTimeMillis() - startTime >= TIMEOUT) {
                PlayerReady = true;  
                break;
            }
                */
            
          
            if (PlayerReady) {
                partitaService.setsGameState(partita, turnState);
                turnState.execute(partita);
            }
        }
    
       
        PlayerReady = false;
    }
    

    public void playerReady() {
        this.PlayerReady = true;
    }

    public Partita getPartita() {
        return tpartita;
    }
}
