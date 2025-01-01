package com.justInTime.model;

import org.springframework.stereotype.Component;
import com.justInTime.service.PartitaService;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class TurnState implements GameState {
    
    @Autowired
    private PartitaService partitaService;

    @Override
    public void execute(Partita partita) {

        Player giocatoreCorrente = partita.getGiocatori().get(partita.getIndiceGiocatoreCorrente());

        int tempoRestante = giocatoreCorrente.getDurataTurno();


        if (giocatoreCorrente.isEscluso()) {
            passaAlProssimoGiocatore(partita);
            return;
        }

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


        if (tempoRestante == 0) {
            giocatoreCorrente.setEscluso(true); 
        }

        passaAlProssimoGiocatore(partita);
    }

    private void passaAlProssimoGiocatore(Partita partita) {
        int indiceCorrente = partita.getIndiceGiocatoreCorrente();
        int prossimoIndice = (indiceCorrente + 1) % partita.getGiocatori().size();
    

        while (partita.getGiocatori().get(prossimoIndice).isEscluso()) {
            prossimoIndice = (prossimoIndice + 1) % partita.getGiocatori().size();
        }
    

        partita.setIndiceGiocatoreCorrente(prossimoIndice);
    

        partitaService.setGameState(partita, new PauseState());
    }
}
