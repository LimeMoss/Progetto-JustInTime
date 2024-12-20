package com.justInTime.model;

import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class TurnState implements GameState {
    
    @Autowired
    private PartitaService partitaService;
    
    private static final int DURATA_TURNO = 30; // 30 secondi per turno

    @Override
    public void execute(Partita partita) {
        Player giocatoreCorrente = partita.getGiocatori().get(partita.getIndiceGiocatoreCorrente());
        //TODO//NOTIFY
        System.out.println("Turno di " + giocatoreCorrente.getName());

        // Impostiamo la durata del turno
        giocatoreCorrente.setDurataTurno(DURATA_TURNO);
        
        int tempoRestante = giocatoreCorrente.getDurataTurno();

        while (tempoRestante > 0) {

            if (giocatoreCorrente.hasFinishedTurn()) {
                break;
            }

            // Riduce il tempo
            tempoRestante--;
            giocatoreCorrente.setDurataTurno(tempoRestante);
            System.out.println("Tempo rimasto per " + giocatoreCorrente.getName() + ": " + tempoRestante + " secondi.");

            try {
                Thread.sleep(1000); // Simula il passare del tempo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Alla fine del turno, passiamo allo stato di pausa
        partitaService.setGameState(partita, new PauseState());
        System.out.println("Turno terminato. In attesa del prossimo giocatore...");
    }
}
