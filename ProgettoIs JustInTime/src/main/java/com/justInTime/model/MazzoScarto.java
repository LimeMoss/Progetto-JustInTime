package com.justInTime.model;

import java.util.ArrayList;
import java.util.List;

public class MazzoScarto extends Mazzo {

    private List<Carta> carteScartate;

    public MazzoScarto() {
        carteScartate = new ArrayList<>();
    }

    @Override
    public void aggiungi(Carta componente) {
        carteScartate.addFirst(componente);
    }

    @Override
    public void rimuovi(Carta componente) {
        carteScartate.remove(componente);
    }

    @Override
    public Carta getCarta(int index) {
        if (index < carteScartate.size()) {
            return carteScartate.get(index);
        }
        return null;
    }

    public Carta ultimaCartaScartata() {
        if (!carteScartate.isEmpty()) {
            return carteScartate.get(carteScartate.size() - 1);
        }
        return null;
    }
}