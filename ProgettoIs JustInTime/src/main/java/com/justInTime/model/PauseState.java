package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

@Component
public class PauseState implements GameState {
    
    @Autowired
    private PartitaService partitaService;
    
    private volatile boolean nextPlayerReady = false;


    /**
     * Esegue le operazioni specifiche di questo stato.
     * Il metodo esegue un loop finche  il flag nextPlayerReady  false.
     * Quando il flag  true, il metodo verifica se la partita  terminata.
     * Se la partita  terminata, il metodo imposta lo stato della partita a EndGameState.
     * Altrimenti, il metodo imposta lo stato della partita a TurnState.
     * 
     * @param partita La partita su cui eseguire le operazioni.
     */
    @Override
    public void execute(Partita partita) {
        


        while (!nextPlayerReady) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        

        nextPlayerReady = false;
        

        if (isGameOver(partita)) {
            partitaService.setGameState(partita.getId(), new EndGameState());
        } else {

            partitaService.setGameState(partita.getId(), new TurnState());
        }
    }
    

    /**
     * Segnala che il giocatore e pronto per continuare la partita.
     * Imposta il flag nextPlayerReady a true, permettendo il proseguimento
     * del gioco dallo stato di pausa.
     */

    public void playerReady() {
        this.nextPlayerReady = true;
    }

    /**
     * Verifica se la partita   terminata.
     * La partita   terminata se almeno un giocatore ha finito le sue carte.
     * @param partita La partita da verificare.
     * @return true se la partita   terminata, false altrimenti.
     */


    private boolean isGameOver(Partita partita) {
        for (Player player : partita.getGiocatori()) {
            if (player.getMano().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
