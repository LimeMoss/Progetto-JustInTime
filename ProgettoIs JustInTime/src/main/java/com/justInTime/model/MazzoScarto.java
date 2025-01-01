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

    public List<Carta> getCarteScartate() {
        return carteScartate;
    }

    public void setCarteScartate(List<Carta> carteScartate) {
        this.carteScartate = carteScartate;
    }

    public int getSize(){
        return carteScartate.size();
    }

    public boolean searchCard(Carta carta){
        return carteScartate.contains(carta);
    }
    public boolean isEmpty() {
        return carteScartate.isEmpty();
    }

}