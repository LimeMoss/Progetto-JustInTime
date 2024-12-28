package com.justInTime.model;

import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class TurnState implements GameState {
    
    @Autowired
    private PartitaService partitaService;
    
    private static final int DURATA_TURNO = 15; 

    @Override
    public void execute(Partita partita) {
        Player giocatoreCorrente = partita.getGiocatori().get(partita.getIndiceGiocatoreCorrente());
    

    
        giocatoreCorrente.setDurataTurno(DURATA_TURNO);
        
        int tempoRestante = giocatoreCorrente.getDurataTurno();

        while (tempoRestante > 0) {

            if (giocatoreCorrente.hasFinishedTurn()) {
                break;
            }

       
            tempoRestante--;
            giocatoreCorrente.setDurataTurno(tempoRestante);

            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        partitaService.setGameState(partita, new PauseState());
    }
}
