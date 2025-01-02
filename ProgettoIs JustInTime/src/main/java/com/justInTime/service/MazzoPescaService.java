package com.justInTime.service;



import org.springframework.stereotype.Service;

import com.justInTime.model.Carta;
import com.justInTime.model.MazzoPesca;

@Service
public class MazzoPescaService {

    /**
     * Aggiunge una carta al mazzo passato come parametro.
     * La carta viene aggiunta in fondo al mazzo.
     * @param mazzo il mazzo a cui aggiungere la carta
     * @param carta la carta da aggiungere
     */
    public void aggiungiCarta(MazzoPesca mazzo, Carta carta) {
        mazzo.aggiungi(carta);
    }

    /**
     * Pesca la carta in cima al mazzo.
     * La carta viene rimossa dal mazzo.
     * @param mazzo il mazzo da cui pescare la carta
     * @return la carta pescata o null se il mazzo è vuoto
     */
    public Carta pescaCarta(MazzoPesca mazzo) {
        return mazzo.pescaCarta();
    }

    /**
     * Rimuove una carta dal mazzo.
     * La carta viene rimossa dal mazzo.
     * @param mazzo il mazzo dal quale rimuovere la carta
     * @param carta la carta da rimuovere
     */
    public void rimuoviCarta(MazzoPesca mazzo, Carta carta) {
        mazzo.rimuovi(carta);
    }

    /**
     * Restituisce la carta all'indice specificato del mazzo.
     * La carta non viene rimossa dal mazzo.
     * @param mazzo il mazzo da cui ottenere la carta
     * @param index l'indice della carta da restituire
     * @return la carta all'indice specificato del mazzo
     */
    public Carta getCarta(MazzoPesca mazzo, int index) {
        return mazzo.getCarta(index);
    }
}
