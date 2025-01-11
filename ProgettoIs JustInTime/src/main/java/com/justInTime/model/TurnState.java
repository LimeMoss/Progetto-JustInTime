package com.justInTime.model;

import org.springframework.stereotype.Component;


import com.justInTime.service.PartitaService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;




@Component("turnState")
public class TurnState implements GameState {

    @Autowired
    @Qualifier("pauseState")
    private GameState pauseState;
    
    private Partita tpartita = new Partita();
    


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

        tpartita=partita;

       

   
     
         Player giocatoreCorrente = partita.getGiocatori().get(partita.getIndiceGiocatoreCorrente());
         int tempoRestante = giocatoreCorrente.getDurataTurno();

     
         if (tuttiEsclusi(partita)) {
             
             partitaService.tempoTerminato(partita);
             return;
         }
     
         if (giocatoreCorrente.isEscluso()) {
             System.out.println("Il giocatore " + giocatoreCorrente.getUtente().getName() + " è già escluso. Passo al prossimo giocatore.");
             passaAlProssimoGiocatore(partita);
             return;
         }
     
         System.out.println("Inizio il turno per il giocatore " + giocatoreCorrente.getUtente().getName());
     
         while (tempoRestante > 0) {
             if (giocatoreCorrente.hasFinishedTurn()) {

                 giocatoreCorrente.setTurnoInPausa(false);
                 break;
             }
     
             tempoRestante--;
             giocatoreCorrente.setDurataTurno(tempoRestante);
    
     
             try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
          
                 Thread.currentThread().interrupt();
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

         partitaService.setsGameState(partita, pauseState);
         
         pauseState.execute(partita);
         
        
     }
     
     private boolean tuttiEsclusi(Partita partita) {
         int counter = 0;
         for (Player giocatore : partita.getGiocatori()) {
             if (giocatore.isEscluso()) {
                 counter++;
             }
         }
     
       
         return counter == partita.getGiocatori().size() - 1;
     }




    public Partita getPartita() {
        return tpartita;
    }

    }


    
