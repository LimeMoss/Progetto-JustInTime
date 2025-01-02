package com.justInTime.service;


import com.justInTime.model.Carta;
import com.justInTime.model.MazzoScarto;
import org.springframework.stereotype.Service;

@Service
public class MazzoScartoService {


    /**
     * Restituisce l'ultima carta scartata.
     * 
     * @return L'ultima carta scartata.
     */
    public Carta ultimaCartaScartata(MazzoScarto mazzoScarto) {
        return mazzoScarto.ultimaCartaScartata();
    }


    /**
     * Aggiunge una carta al mazzo scarto.
     * 
     * @param carta La carta da aggiungere al mazzo scarto.
     */
    public void aggiungiCarta(MazzoScarto mazzoScarto, Carta carta) {
        mazzoScarto.aggiungi(carta);
    }

    /**
     * Rimuove una carta dal mazzo scarto.
     * 
     * @param carta La carta da rimuovere dal mazzo scarto.
     */
    public void rimuoviCarta(MazzoScarto mazzoScarto, Carta carta) {
        mazzoScarto.rimuovi(carta);
    }

    /**
     * Restituisce la carta al l'indice specificato del mazzo scarto.
     * 
     * @param index L'indice della carta da restituire.
     * @return La carta al l'indice specificato del mazzo scarto.
     */
    public Carta getCarta(MazzoScarto mazzoScarto,int index) {
        return mazzoScarto.getCarta(index);
    }
}