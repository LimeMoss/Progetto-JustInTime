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
    private static final long TIMEOUT = 60000;

    /**
     * Esegue le operazioni necessarie per mettere in pausa una partita.
     * Il metodo va in attesa che il giocatore successivo si renda disponibile
     * per continuare la partita. Se il timeout scade, la partita viene terminata.
     *
     * Il metodo utilizza un ciclo che verifica se il flag nextPlayerReady
     * true, in tal caso esce dal ciclo e verifica se la partita terminata.
     * Se la partita non terminata, il metodo imposta lo stato di gioco
     * a "turnState" e lo esegue.
     *
     * @param partita La partita su cui eseguire le operazioni.
     */
    @Override
    public void execute(Partita partita) {
        nextPlayerReady = false;

        if (!waitForPlayer()) {

            if (isGameOver(partita)) {
                handleGameOver(partita);
            } else {

                NextTurn(partita);
            }
        }
    }

    /**
     * Attende che il giocatore sia pronto o che scada il timeout.
     *
     * @return true se il giocatore è pronto, false se il timeout è scaduto
     */
    private boolean waitForPlayer() {
        long startTime = System.currentTimeMillis();
        while (!nextPlayerReady) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

       
            if (System.currentTimeMillis() - startTime >= TIMEOUT) {
                nextPlayerReady = true;
                return false; 
            }
        }
        return true; 
    }

    /**
     * Gestisce il caso di game over, cambiando lo stato della partita.
     *
     * @param partita La partita da gestire.
     */
    private void handleGameOver(Partita partita) {
        partitaService.setsGameState(partita, endGameState);
        endGameState.execute(partita);
    }

    /**
     * Gestisce il proseguimento del gioco, cambiando lo stato della partita al
     * turno successivo.
     *
     * @param partita La partita da gestire.
     */
    private void NextTurn(Partita partita) {
        if (nextPlayerReady) {
            partitaService.setsGameState(partita, turnState);
            turnState.execute(partita);
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
