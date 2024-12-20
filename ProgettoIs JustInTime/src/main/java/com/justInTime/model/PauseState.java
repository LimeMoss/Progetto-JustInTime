package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

/**
 * Rappresenta lo stato di pausa di una partita.
 * Quando il gioco è in pausa, il gioco attende che il prossimo giocatore sia pronto prima di continuare.
 * Se la partita è finita, il gioco passa allo stato finale; altrimenti, si riprende il turno.
 */
@Component
public class PauseState implements GameState {
    
    /**
     * Servizio che gestisce le operazioni relative alla partita.
     */
    @Autowired
    private PartitaService partitaService;
    
    /**
     * Indica se il prossimo giocatore è pronto per riprendere il gioco.
     * Questo campo è volatile per garantire che il valore sia sempre aggiornato correttamente nei thread.
     */
    private volatile boolean nextPlayerReady = false;

    /**
     * Esegue l'azione corrispondente allo stato di pausa.
     * In questo stato, il gioco attende che il prossimo giocatore sia pronto per continuare.
     * Se la partita è finita, cambia lo stato del gioco a fine partita.
     * Altrimenti, passa al prossimo turno.
     *
     * @param partita la partita su cui operare.
     */
    @Override
    public void execute(Partita partita) {
        System.out.println("Gioco in pausa. In attesa che il prossimo giocatore sia pronto...");
        
        // Passiamo al prossimo giocatore
        partitaService.passaAlProssimoGiocatore(partita);
        
        // Aspettiamo che il prossimo giocatore sia pronto
        while (!nextPlayerReady) {
            try {
                Thread.sleep(100); // Attende per un breve periodo prima di controllare di nuovo
            } catch (InterruptedException e) {
                e.printStackTrace(); // Gestione dell'interruzione del thread
            }
        }
        
        // Reset dello stato
        nextPlayerReady = false;
        
        // Controlliamo se la partita è finita
        if (isGameOver(partita)) {
            partitaService.setGameState(partita, new EndGameState()); // Impostiamo lo stato di fine partita
        } else {
            // Altrimenti, passiamo al prossimo turno
            partitaService.setGameState(partita, new TurnState()); // Impostiamo lo stato del turno
        }
    }
    
    /**
     * Metodo chiamato quando il prossimo giocatore è pronto per riprendere il gioco.
     * Imposta la variabile `nextPlayerReady` a true per indicare che il gioco può continuare.
     */
    public void playerReady() {
        this.nextPlayerReady = true;
    }
    
    /**
     * Controlla se la partita è finita.
     * La partita è considerata finita se un giocatore ha finito le carte.
     *
     * @param partita la partita da verificare.
     * @return true se la partita è finita, false altrimenti.
     */
    private boolean isGameOver(Partita partita) {
        // Implementa la logica per determinare se il gioco è finito
        // Ad esempio, se un giocatore ha finito le carte
        for (Player player : partita.getGiocatori()) {
            if (player.getMano().isEmpty()) {
                return true; // Il gioco è finito se un giocatore non ha più carte
            }
        }
        return false; // Il gioco continua se nessun giocatore ha finito le carte
    }
}
