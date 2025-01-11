package com.justInTime.model;

/**
 * Interfaccia che rappresenta uno stato di gioco.
 * Gli stati di gioco implementano questa interfaccia per definire un comportamento specifico
 * tramite il metodo {@code execute}.
 */
public interface GameState {

    /**
     * Esegue le operazioni specifiche dello stato corrente sul contesto della partita.
     * 
     * @param partita La partita su cui eseguire le operazioni.
     */
    void execute(Partita partita);
    Partita getPartita();
}
