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

    public Carta ultimaCartaScartata() {
        return ((MazzoScarto) mazzoScarto).ultimaCartaScartata();
    }

    public void aggiungiCarta(Carta carta) {
        mazzoScarto.aggiungi(carta);
    }

    public void rimuoviCarta(Carta carta) {
        mazzoScarto.rimuovi(carta);
    }

    public Carta getCarta(int index) {
        return mazzoScarto.getCarta(index);
    }
}