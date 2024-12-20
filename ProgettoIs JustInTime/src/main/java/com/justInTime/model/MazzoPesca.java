package com.justInTime.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe che rappresenta un Mazzo di Pesca.
 * Estende la classe astratta {@link Mazzo} e implementa le operazioni specifiche
 * per un mazzo utilizzato per pescare carte durante il gioco.
 */
public class MazzoPesca extends Mazzo {

    private List<Carta> carte; // Lista delle carte presenti nel mazzo

    /**
     * Costruttore che inizializza il mazzo di pesca.
     * Genera automaticamente le carte e le mescola.
     */
    public MazzoPesca() {
        carte = new ArrayList<>();
        generaCartePesca();
        Collections.shuffle(carte);
    }

    /**
     * Aggiunge una carta al mazzo in prima posizione.
     * 
     * @param componente La carta da aggiungere.
     */
    @Override
    public void aggiungi(Carta componente) {
        carte.add(0, componente);
    }

    /**
     * Rimuove una carta specifica dal mazzo.
     * 
     * @param componente La carta da rimuovere.
     */
    @Override
    public void rimuovi(Carta componente) {
        carte.remove(componente);
    }

    /**
     * Restituisce una carta in base all'indice specificato.
     * 
     * @param index L'indice della carta da recuperare.
     * @return La carta corrispondente all'indice, oppure {@code null} se l'indice è fuori dai limiti.
     */
    @Override
    public Carta getCarta(int index) {
        if (index < carte.size()) {
            return carte.get(index);
        }
        return null;
    }

    /**
     * Pesca una carta dalla cima del mazzo.
     * 
     * @return La carta pescata, oppure {@code null} se il mazzo è vuoto.
     */
    public Carta pescaCarta() {
        if (!carte.isEmpty()) {
            return carte.remove(0);
        }
        return null;
    }

    /**
     * Genera le carte del mazzo di pesca.
     * Include carte numerate, carte speciali "Accelera" e "Rallenta", e carte "Jolly".
     */
    private void generaCartePesca() {
        for (int i = 0; i < 6; i++) {
            for (int valore = 0; valore <= 9; valore++) {
                carte.add(new Carta(valore));
            }
        }

        for (int i = 0; i < 6; i++) {
            carte.add(new Carta("Accelera"));
            carte.add(new Carta("Rallenta"));
        }

        for (int i = 0; i < 4; i++) {
            carte.add(new Carta("Jolly"));
        }
    }

    /**
     * Restituisce il numero di carte attualmente presenti nel mazzo.
     * 
     * @return Il numero di carte nel mazzo.
     */
    public int getSize() {
        return carte.size();
    }

    /**
     * Verifica se una carta specifica è presente nel mazzo.
     * 
     * @param carta La carta da cercare.
     * @return {@code true} se la carta è presente, altrimenti {@code false}.
     */
    public boolean searchCard(Carta carta) {
        return carte.contains(carta);
    }
}
