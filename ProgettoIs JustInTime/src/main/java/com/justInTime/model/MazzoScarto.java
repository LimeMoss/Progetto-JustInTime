package com.justInTime.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un Mazzo di Scarto.
 * Estende la classe astratta {@link Mazzo} e implementa le operazioni specifiche
 * per gestire le carte scartate durante il gioco.
 */
public class MazzoScarto extends Mazzo {

    private List<Carta> carteScartate; // Lista delle carte scartate

    /**
     * Costruttore che inizializza un mazzo di scarto vuoto.
     */
    public MazzoScarto() {
        carteScartate = new ArrayList<>();
    }

    /**
     * Aggiunge una carta al mazzo di scarto in prima posizione.
     * 
     * @param componente La carta da aggiungere.
     */
    @Override
    public void aggiungi(Carta componente) {
        carteScartate.add(0, componente);
    }

    /**
     * Rimuove una carta specifica dal mazzo di scarto.
     * 
     * @param componente La carta da rimuovere.
     */
    @Override
    public void rimuovi(Carta componente) {
        carteScartate.remove(componente);
    }

    /**
     * Restituisce una carta dal mazzo di scarto in base all'indice specificato.
     * 
     * @param index L'indice della carta da recuperare.
     * @return La carta corrispondente all'indice, oppure {@code null} se l'indice è fuori dai limiti.
     */
    @Override
    public Carta getCarta(int index) {
        if (index < carteScartate.size()) {
            return carteScartate.get(index);
        }
        return null;
    }

    /**
     * Restituisce l'ultima carta scartata dal mazzo.
     * 
     * @return L'ultima carta scartata, oppure {@code null} se il mazzo è vuoto.
     */
    public Carta ultimaCartaScartata() {
        if (!carteScartate.isEmpty()) {
            return carteScartate.get(carteScartate.size() - 1);
        }
        return null;
    }

    /**
     * Restituisce il numero di carte presenti nel mazzo di scarto.
     * 
     * @return Il numero di carte nel mazzo di scarto.
     */
    public int getSize() {
        return carteScartate.size();
    }

    /**
     * Verifica se una carta specifica è presente nel mazzo di scarto.
     * 
     * @param carta La carta da cercare.
     * @return {@code true} se la carta è presente, altrimenti {@code false}.
     */
    public boolean searchCard(Carta carta) {
        return carteScartate.contains(carta);
    }
}
