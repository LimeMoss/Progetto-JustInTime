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
        System.out.println("Entrando nello stato PauseState.");
        nextPlayerReady = false;
    
        if (!waitForPlayer()) {
            System.out.println("Timeout scaduto o interruzione rilevata durante l'attesa del giocatore.");
    
            if (isGameOver(partita)) {
                System.out.println("La partita è terminata. Passaggio allo stato EndGameState.");
                handleGameOver(partita);
            } else {
                System.out.println("Timeout scaduto. Passaggio al turno successivo.");
                NextTurn(partita);
            }
        } else {
            System.out.println("Il giocatore è pronto. Ripresa del gioco.");
        }
    }
    
    /**
     * Attende che il giocatore sia pronto o che scada il timeout.
     */
    private boolean waitForPlayer() {
        long startTime = System.currentTimeMillis();
        System.out.println("In attesa che il giocatore sia pronto...");
    
        while (!nextPlayerReady) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Thread interrotto durante l'attesa del giocatore.");
                Thread.currentThread().interrupt();
                return false;
            }
    
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= TIMEOUT) {
                System.out.println("Timeout di " + TIMEOUT + "ms raggiunto. Il giocatore non è pronto.");
                return false;
            }
        }
    
        System.out.println("Il giocatore è pronto prima del timeout.");
        return true;
    }
    
    /**
     * Gestisce il caso di game over.
     */
    private void handleGameOver(Partita partita) {
        System.out.println("Gestione del game over per la partita.");
        partitaService.setsGameState(partita, endGameState);
        endGameState.execute(partita);
    }
    
    /**
     * Gestisce il proseguimento del turno.
     */
    private void NextTurn(Partita partita) {
        if (nextPlayerReady) {
            System.out.println("Passaggio al turno successivo.");
            partitaService.setsGameState(partita, turnState);
            turnState.execute(partita);
        } else {
            System.out.println("Errore: il giocatore non è pronto ma è stato chiamato NextTurn.");
        }
    }
    
    /**
     * Segnala che il giocatore è pronto.
     */
    public void playerReady() {
        this.nextPlayerReady = true;
        System.out.println("Il giocatore ha segnalato di essere pronto.");
    }
    
    /**
     * Verifica se la partita è terminata.
     */
    private boolean isGameOver(Partita partita) {
        System.out.println("Verifica se la partita è terminata.");
        for (Player player : partita.getGiocatori()) {
            if (player.getMano().isEmpty()) {
                System.out.println("Il giocatore " + player.getUtente().getName() + " ha finito le sue carte. Partita terminata.");
                playerService.addVictory(player.getId());
                return true;
            }
        }
        System.out.println("La partita non è ancora terminata.");
        return false;
    }
}