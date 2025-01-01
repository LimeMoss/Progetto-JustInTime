package com.justInTime.service;



import org.springframework.stereotype.Service;

import com.justInTime.model.Carta;
import com.justInTime.model.Mazzo;
import com.justInTime.model.MazzoFactory;
import com.justInTime.model.MazzoPesca;

@Service
public class MazzoPescaService {

    private final Mazzo mazzoPesca;

    public MazzoPescaService() {
        this.mazzoPesca =  MazzoFactory.createMazzo("pesca");
    }

    /**
     * Pesca la carta in cima al mazzo.
     * La carta viene rimossa dal mazzo.
     * @return la carta pescata o null se il mazzo  vuoto
     */
    public Carta pescaCarta() {
        return ((MazzoPesca) mazzoPesca).pescaCarta();
    }

    /**
     * Aggiunge una carta al mazzo pesca.
     * La carta viene aggiunta in fondo al mazzo.
     * @param carta la carta da aggiungere
     */
    public void aggiungiCarta(Carta carta) {
        mazzoPesca.aggiungi(carta);
    }

    /**
     * Rimuove una carta dal mazzo pesca.
     * La carta viene rimossa dal mazzo.
     * @param carta la carta da rimuovere
     */
    public void rimuoviCarta(Carta carta) {
        mazzoPesca.rimuovi(carta);
    }

    /**
     * Restituisce la carta al l'indice specificato del mazzo pesca.
     * La carta non viene rimossa dal mazzo.
     * @param index l'indice della carta da restituire
     * @return la carta al l'indice specificato del mazzo pesca
     */
    public Carta getCarta(int index) {
        return mazzoPesca.getCarta(index);
    }
    
    
}
