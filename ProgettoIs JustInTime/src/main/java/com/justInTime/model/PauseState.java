package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

@Component
public class PauseState implements GameState {
    
    @Autowired
    private PartitaService partitaService;
    
    private volatile boolean nextPlayerReady = false;

    @Override
    public void execute(Partita partita) {
        System.out.println("Gioco in pausa. In attesa che il prossimo giocatore sia pronto...");
        
        // Passiamo al prossimo giocatore
        partitaService.passaAlProssimoGiocatore(partita);
        
        // Aspettiamo che il prossimo giocatore sia pronto
        while (!nextPlayerReady) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Reset dello stato
        nextPlayerReady = false;
        
        // Controlliamo se la partita è finita
        if (isGameOver(partita)) {
            partitaService.setGameState(partita, new EndGameState());
        } else {
            // Altrimenti, passiamo al prossimo turno
            partitaService.setGameState(partita, new TurnState());
        }
    }
    
    public void playerReady() {
        this.nextPlayerReady = true;
    }
    
    private boolean isGameOver(Partita partita) {
        // Implementa la logica per determinare se il gioco è finito
        // Per esempio, se un giocatore ha finito le carte
        for (Player player : partita.getGiocatori()) {
            if (player.getMano().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
