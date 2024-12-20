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

    public Carta pescaCarta() {
        return ((MazzoPesca) mazzoPesca).pescaCarta();
    }

    public void aggiungiCarta(Carta carta) {
        mazzoPesca.aggiungi(carta);
    }

    public void rimuoviCarta(Carta carta) {
        mazzoPesca.rimuovi(carta);
    }

    public Carta getCarta(int index) {
        return mazzoPesca.getCarta(index);
    }
    
    
}
