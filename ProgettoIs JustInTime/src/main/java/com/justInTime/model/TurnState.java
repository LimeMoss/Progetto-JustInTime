package com.justInTime.model;

import org.springframework.stereotype.Component;
import com.justInTime.service.PartitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


@Component("turnState")
public class TurnState implements GameState {

    @Autowired
    @Lazy
    private GameState pauseState;


    @Autowired
    @Lazy
    private PartitaService partitaService;

    /**
     * Esegue le operazioni specifiche di questo stato.
     * Verifica se il giocatore corrente escluso, se si , passa al prossimo
     * giocatore.
     * Altrimenti, crea un loop finche il tempo del turno del giocatore corrente
     * maggiore di 0.
     * Ad ogni iterazione, decrementa il tempo del turno del giocatore corrente di
     * 1,
     * verifica se il giocatore ha finito il suo turno e se si , esce dal loop.
     * Se il tempo del turno del giocatore corrente 0, esclude il giocatore.
     * Infine, passa al prossimo giocatore.
     * 
     * @param partita La partita su cui eseguire le operazioni.
     */
    @Override
    public void execute(Partita partita) {

        Player giocatoreCorrente = partita.getGiocatori().get(partita.getIndiceGiocatoreCorrente());

        int tempoRestante = giocatoreCorrente.getDurataTurno();


    
        if (tuttiEsclusi(partita)) {
            
            partitaService.tempoTerminato(partita.getId());

        }

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

    /**
     * Passa il turno al prossimo giocatore non escluso nella partita.
     * Aggiorna l'indice del giocatore corrente e imposta lo stato della partita
     * su {@link PauseState} in attesa che il prossimo giocatore sia pronto.
     *
     * @param partita La partita corrente in cui si esegue il passaggio di turno.
     */

    private void passaAlProssimoGiocatore(Partita partita) {
        int indiceCorrente = partita.getIndiceGiocatoreCorrente();
        int prossimoIndice = (indiceCorrente + 1) % partita.getGiocatori().size();

        while (partita.getGiocatori().get(prossimoIndice).isEscluso()) {
            prossimoIndice = (prossimoIndice + 1) % partita.getGiocatori().size();
        }

        partita.setIndiceGiocatoreCorrente(prossimoIndice);


        partitaService.setsGameState(partita.getId(), pauseState);

    
    }

    /**
     * Verifica se tutti i giocatori, ad eccezione di uno, sono stati esclusi
     * dalla partita.
     * 
     * @param partita La partita corrente
     * @return true se tutti i giocatori, ad eccezione di uno, sono stati esclusi,
     *         false altrimenti
     */
    private boolean tuttiEsclusi(Partita partita) {
        int counter = 0;
        for (Player giocatore : partita.getGiocatori()) {
            if (giocatore.isEscluso()) {
                counter++;
            }
        }
        if (counter == partita.getGiocatori().size() - 1) {
            return true;
        }
        return false;
    }

}