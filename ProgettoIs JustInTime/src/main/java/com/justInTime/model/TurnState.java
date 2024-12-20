package com.justInTime.model;

import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * La classe TurnState rappresenta lo stato di gioco in cui un giocatore ha il proprio turno.
 * Implementa l'interfaccia {@link GameState}.
 */
@Component
public class TurnState implements GameState {

    @Autowired
    private PartitaService partitaService;

    private static final int DURATA_TURNO = 30; // Durata di un turno in secondi.

    /**
     * Esegue la logica dello stato del turno per la partita specificata.
     * 
     * @param partita l'oggetto {@link Partita} su cui eseguire lo stato.
     */
    @Override
    public void execute(Partita partita) {
        // Ottieni il giocatore corrente
        Player giocatoreCorrente = partita.getGiocatori().get(partita.getIndiceGiocatoreCorrente());

        // Notifica l'inizio del turno
        System.out.println("Turno di " + giocatoreCorrente.getName());

        // Imposta la durata del turno per il giocatore
        giocatoreCorrente.setDurataTurno(DURATA_TURNO);
        int tempoRestante = giocatoreCorrente.getDurataTurno();

        // Ciclo per gestire il conto alla rovescia del turno
        while (tempoRestante > 0) {
            if (giocatoreCorrente.hasFinishedTurn()) {
                // Interrompe il turno se il giocatore ha finito
                break;
            }

            // Riduce il tempo rimanente di 1 secondo
            tempoRestante--;
            giocatoreCorrente.setDurataTurno(tempoRestante);
            System.out.println("Tempo rimasto per " + giocatoreCorrente.getName() + ": " + tempoRestante + " secondi.");

            try {
                // Simula il passare del tempo con una pausa di 1 secondo
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Passa allo stato di pausa dopo il termine del turno
        partitaService.setGameState(partita, new PauseState());
        System.out.println("Turno terminato. In attesa del prossimo giocatore...");
    }
}
