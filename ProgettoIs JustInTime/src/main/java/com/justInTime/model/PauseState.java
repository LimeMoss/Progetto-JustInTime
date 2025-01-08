package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import com.justInTime.service.PartitaService;
import com.justInTime.service.PlayerService;

@Component("pauseState")
public class PauseState implements GameState {

    @Autowired
    private PartitaService partitaService;

    @Autowired
    private PlayerService playerService;

    private volatile boolean nextPlayerReady = false;

    @Autowired
    @Qualifier("turnState")
    @Lazy
    private GameState turnState;

    @Autowired
    @Qualifier("endGameState")
    @Lazy
    private GameState endGameState;
    // TODO IMPOSTARE TEMPO DI TIMEOUT A 30000 (30 secondi)//
    private static final long TIMEOUT = 30000;

    /**
     * Esegue le operazioni specifiche di questo stato.
     * Il metodo esegue un loop finché il flag nextPlayerReady è false.
     * Quando il flag è true, il metodo verifica se la partita è terminata.
     * Se la partita è terminata, il metodo imposta lo stato della partita a
     * EndGameState.
     * Altrimenti, il metodo imposta lo stato della partita a TurnState.
     *
     * @param partita La partita su cui eseguire le operazioni.
     */
    @Override
    public void execute(Partita partita) {
        long startTime = System.currentTimeMillis();

        while (!nextPlayerReady) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Controllo timeout
            if (System.currentTimeMillis() - startTime >= TIMEOUT) {
                nextPlayerReady = true; 
                break; 
            }
        }
        nextPlayerReady = false;
       
        if (isGameOver(partita)) {
            partitaService.setsGameState(partita.getId(), endGameState);
        } else {
            partitaService.setsGameState(partita.getId(), turnState);
        }

        
    }

    /**
     * Segnala che il giocatore è pronto per continuare la partita.
     * Imposta il flag nextPlayerReady a true, permettendo il proseguimento
     * del gioco dallo stato di pausa.
     */
    public void playerReady() {
        this.nextPlayerReady = true;
    }

    /**
     * Verifica se la partita è terminata.
     * La partita è terminata se almeno un giocatore ha finito le sue carte.
     * 
     * @param partita La partita da verificare.
     * @return true se la partita è terminata, false altrimenti.
     */
    private boolean isGameOver(Partita partita) {
        for (Player player : partita.getGiocatori()) {
            if (player.getMano().isEmpty()) {
                playerService.addVictory(player.getId());
                return true;
            }
        }
        return false;
    }
}
