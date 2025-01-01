package com.justInTime.service;


import com.justInTime.model.Carta;
import com.justInTime.model.Mazzo;
import com.justInTime.model.MazzoFactory;
import com.justInTime.model.MazzoScarto;
import org.springframework.stereotype.Service;

@Service
public class MazzoScartoService {

    private final Mazzo mazzoScarto;

    public MazzoScartoService() {
         this.mazzoScarto =  MazzoFactory.createMazzo("scarto");
    }

    /**
     * Restituisce l'ultima carta scartata.
     * 
     * @return L'ultima carta scartata.
     */
    public Carta ultimaCartaScartata() {
        return ((MazzoScarto) mazzoScarto).ultimaCartaScartata();
    }


    /**
     * Aggiunge una carta al mazzo scarto.
     * 
     * @param carta La carta da aggiungere al mazzo scarto.
     */
    public void aggiungiCarta(Carta carta) {
        mazzoScarto.aggiungi(carta);
    }

    /**
     * Rimuove una carta dal mazzo scarto.
     * 
     * @param carta La carta da rimuovere dal mazzo scarto.
     */
    public void rimuoviCarta(Carta carta) {
        mazzoScarto.rimuovi(carta);
    }

    /**
     * Restituisce la carta al l'indice specificato del mazzo scarto.
     * 
     * @param index L'indice della carta da restituire.
     * @return La carta al l'indice specificato del mazzo scarto.
     */
    public Carta getCarta(int index) {
        return mazzoScarto.getCarta(index);
    }
}