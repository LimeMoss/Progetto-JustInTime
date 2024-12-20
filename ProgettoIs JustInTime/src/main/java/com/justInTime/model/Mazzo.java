package com.justInTime.model;

/**
 * Classe astratta che rappresenta un mazzo di carte.
 * Fornisce un'interfaccia per l'aggiunta, la rimozione e il recupero di carte nel mazzo.
 */
public abstract class Mazzo {

    /**
     * Aggiunge una carta al mazzo.
     *
     * @param componente La carta da aggiungere.
     */
    public abstract void aggiungi(Carta componente);

    /**
     * Rimuove una carta dal mazzo.
     *
     * @param componente La carta da rimuovere.
     */
    public abstract void rimuovi(Carta componente);

    /**
     * Recupera una carta dal mazzo in base all'indice specificato.
     *
     * @param index L'indice della carta da recuperare.
     * @return La carta corrispondente all'indice specificato.
     */
    public abstract Carta getCarta(int index);
}
