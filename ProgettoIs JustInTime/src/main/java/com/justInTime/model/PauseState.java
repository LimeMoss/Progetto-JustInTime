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

    private Partita Tpartita = new Partita();

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
    
        
        Tpartita = partita;
        nextPlayerReady = false;
    
     
        long startTime = System.currentTimeMillis();
        System.out.println("In attesa che il giocatore sia pronto...");
    
   
        while (!nextPlayerReady) {
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
                break; 
            }
    
    
            long elapsedTime = System.currentTimeMillis() - startTime;
            /*if (elapsedTime >= TIMEOUT) {
                System.out.println("Timeout raggiunto. Il giocatore non è pronto.");
                nextPlayerReady = true;
                break;
            }
                */
        }
    
        if (nextPlayerReady) {
            System.out.println("Il giocatore è pronto prima del timeout.");
    
           
            boolean gameOver = false;
            for (Player player : partita.getGiocatori()) {
                if (player.getMano().isEmpty()) {
                    System.out.println("Il giocatore " + player.getUtente().getName() + " ha finito le sue carte. Partita terminata.");
                    playerService.addVictory(player.getId());
                    gameOver = true;
                    break; 
                }
            }
    
            if (gameOver) {
                System.out.println("La partita è terminata. Imposto lo stato di gioco su endGameState.");
                partitaService.setsGameState(partita, endGameState); 
                endGameState.execute(partita); 
            } else {
    
                System.out.println("Passaggio al turno successivo.");
                partita.setGameState(turnState); 
                turnState.execute(partita); 
            }
        }
    }
    
    public void setNextPlayerReady(boolean nextPlayerReady) {
        this.nextPlayerReady = nextPlayerReady;
    }

    public void notifyPlayerReady(Long partitaId) {
        Partita partita = partitaService.getPartita(partitaId);
        if (partita != null && partita.getGameState() instanceof PauseState) {
            setNextPlayerReady(true);
            System.out.println("Il giocatore è pronto, variabile 'nextPlayerReady' impostata.");
            System.out.println("Partita ID di cui setto nextPlayerReady: " + partitaId);
        }
    }

    public boolean isNextPlayerReady() {
        return nextPlayerReady;
    }

    /**
     * Verifica se la partita è terminata.
     */
    private boolean isGameOver(Partita partita) {
        System.out.println("Verifica se la partita è terminata.");
        for (Player player : partita.getGiocatori()) {
            if (player.getMano().isEmpty()) {
                System.out.println(
                        "Il giocatore " + player.getUtente().getName() + " ha finito le sue carte. Partita terminata.");
                playerService.addVictory(player.getId());
                return true;
            }
        }
        System.out.println("La partita non è ancora terminata.");
        return false;
    }

    public Partita getPartita() {
        return Tpartita;
    }

}