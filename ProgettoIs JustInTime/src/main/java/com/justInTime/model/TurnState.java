package com.justInTime.model;

import org.springframework.stereotype.Component;


import com.justInTime.service.PartitaService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@Component("turnState")
public class TurnState implements GameState {

    @Autowired
    @Qualifier("pauseState")
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
        // TODO // IL CODICE per prendere il nome e metterlo nella sessione è questo. Però non va bene dove sta ora. va inserito nel while, esattamente non so dove sono troppo stanco//        
        try {
         
            @SuppressWarnings("deprecation")
            HttpSession session = ((HttpServletRequest) Thread.currentThread().getContextClassLoader().loadClass("com.justInTime.controller.PartitaController").newInstance()).getSession(true);
            session.setAttribute("partitaInGame", partita);
        } catch (Exception e) {
      
        }
       

         System.out.println("Esecuzione dello stato TurnState avviata.");
     
         Player giocatoreCorrente = partita.getGiocatori().get(partita.getIndiceGiocatoreCorrente());
         int tempoRestante = giocatoreCorrente.getDurataTurno();
         System.out.println("Giocatore corrente: " + giocatoreCorrente.getUtente().getName() + ", Tempo rimanente: " + tempoRestante);
     
         if (tuttiEsclusi(partita)) {
             System.out.println("Tutti i giocatori, eccetto uno, sono esclusi. Terminando la partita.");
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
                 System.out.println("Il giocatore " + giocatoreCorrente.getUtente().getName() + " ha terminato il turno.");
                 giocatoreCorrente.setTurnoInPausa(false);
                 break;
             }
     
             tempoRestante--;
             giocatoreCorrente.setDurataTurno(tempoRestante);
             System.out.println("Tempo rimanente per il giocatore " + giocatoreCorrente.getUtente().getName() + ": " + tempoRestante);
     
             try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 System.out.println("Thread interrotto durante il turno del giocatore " + giocatoreCorrente.getUtente().getName());
                 Thread.currentThread().interrupt();
             }
         }
     
         if (tempoRestante == 0) {
             giocatoreCorrente.setEscluso(true);
             System.out.println("Il giocatore " + giocatoreCorrente.getUtente().getName() + " è stato escluso per aver terminato il tempo.");
         }
     
         passaAlProssimoGiocatore(partita);
    
              }
              
      private void passaAlProssimoGiocatore(Partita partita) {
         System.out.println("Passando al prossimo giocatore...");
         int indiceCorrente = partita.getIndiceGiocatoreCorrente();
         int prossimoIndice = (indiceCorrente + 1) % partita.getGiocatori().size();
     
         while (partita.getGiocatori().get(prossimoIndice).isEscluso()) {
             System.out.println("Il giocatore " + partita.getGiocatori().get(prossimoIndice).getUtente().getName() + " è escluso. Continuo la ricerca.");
             prossimoIndice = (prossimoIndice + 1) % partita.getGiocatori().size();
         }
     
         partita.setIndiceGiocatoreCorrente(prossimoIndice);
         System.out.println("Il prossimo giocatore è " + partita.getGiocatori().get(prossimoIndice).getUtente().getName());
     
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
     
         System.out.println("Numero di giocatori esclusi: " + counter + " su " + partita.getGiocatori().size());
         return counter == partita.getGiocatori().size() - 1;
     }

        /**
         * Ritorna il giocatore successivo non escluso, oppure null se tutti i giocatori
         * sono esclusi.
         * 
         * @param partita la partita corrente
         * @return il prossimo giocatore non escluso, oppure null se tutti i giocatori
         *         sono esclusi.
         */
        public Player getPlayerSuccessivo(Partita partita) {
            int indiceCorrente = partita.getIndiceGiocatoreCorrente();
            int prossimoIndice = (indiceCorrente + 1) % partita.getGiocatori().size();  
    
            while (partita.getGiocatori().get(prossimoIndice).isEscluso()) {
                prossimoIndice = (prossimoIndice + 1) % partita.getGiocatori().size();
                
            
                if (prossimoIndice == indiceCorrente) {
                    return null;
                }
            }
        
            return partita.getGiocatori().get(prossimoIndice);
        }

    }


    
