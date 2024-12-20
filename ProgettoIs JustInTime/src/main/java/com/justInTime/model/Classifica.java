package com.justInTime.model;

import java.util.List;

/**
 * Classe che rappresenta una classifica di gioco, contenente una lista di giocatori.
 * La classifica permette di gestire i giocatori partecipanti e i loro punteggi.
 */
public class Classifica {

    private List<Player> giocatori;  // Lista dei giocatori nella classifica

    /**
     * Costruttore predefinito per creare una classifica vuota.
     */
    public Classifica() {}

    /**
     * Restituisce la lista dei giocatori nella classifica.
     *
     * @return La lista dei giocatori.
     */
    public List<Player> getGiocatori() {
        return giocatori;
    }

    /**
     * Imposta la lista dei giocatori nella classifica.
     *
     * @param giocatori La nuova lista dei giocatori.
     */
    public void setGiocatori(List<Player> giocatori) {
        this.giocatori = giocatori;
    }

}
