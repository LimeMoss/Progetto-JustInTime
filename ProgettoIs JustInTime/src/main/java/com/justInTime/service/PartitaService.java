// PartitaService.java
package com.justInTime.service;

import com.justInTime.model.*;

public class PartitaService {

    // Avvia la partita e gestisce il flusso di gioco
    public void play(Partita partita) {
        if (partita.getGameState() == null) {
            iniziaPartita(partita); // Se la partita non è ancora iniziata, avviala
        }

        while (!(partita.getGameState() instanceof EndGameState)) {
            partita.getGameState().execute(partita);  // Esegui l'azione dello stato corrente
        }
    }

    // Inizia la partita
    public void iniziaPartita(Partita partita) {
        partita.setGameState(new StartGameState()); // Inizializza lo stato di inizio partita
        partita.setIndiceGiocatoreCorrente(0);
        partita.getGameState().execute(partita);
    }

    // Inizia il turno del giocatore corrente
    public void iniziaTurno(Partita partita) {
        Player giocatoreCorrente = partita.getGiocatoreCorrente();
        System.out.println("Il turno di " + giocatoreCorrente.getName() + " è iniziato.");
        
        partita.setGameState(new TurnState());
        partita.getGameState().execute(partita);
    }

    // Passa al prossimo giocatore
    public void passaAlProssimoGiocatore(Partita partita) {
        partita.setIndiceGiocatoreCorrente((partita.getIndiceGiocatoreCorrente() + 1) % partita.getGiocatori().size());
    }

    // Termina la partita
    public void terminaPartita(Partita partita) {
        if (!(partita.getGameState() instanceof EndGameState)) {
            partita.setGameState(new EndGameState());
            System.out.println("La partita è stata terminata.");
        }
    }

    // Aggiunge un giocatore alla partita
    public boolean aggiungiGiocatore(Partita partita, Player giocatore) {
        if (partita.getGiocatori().size() < 4 && !partita.getGiocatori().contains(giocatore)) {
            partita.getGiocatori().add(giocatore);
            giocatore.getPartite().add(partita);
            return true;
        } else {
            System.out.println("Limite massimo di giocatori raggiunto o giocatore già presente.");
            return false;
        }
    }

    // Rimuove un giocatore dalla partita
    public boolean rimuoviGiocatore(Partita partita, Player giocatore) {
        if (partita.getGiocatori().contains(giocatore)) {
            partita.getGiocatori().remove(giocatore);
            giocatore.getPartite().remove(partita);
            return true;
        } else {
            System.out.println("Giocatore non presente in questa partita.");
            return false;
        }
    }

    // Distribuisci carte iniziali
    public void distribuisciCarteIniziali(Partita partita) {
        for (Player giocatore : partita.getGiocatori()) {
            for (int i = 0; i < 5; i++) {
                
                giocatore.aggiungiCartaAllaMano(partita.getMazzoNormale().pescaCarta());
            }
        }
    }

    // Imposta lo stato della partita
    public void setGameState(Partita partita, GameState gameState) {
        partita.setGameState(gameState);
    }
}
