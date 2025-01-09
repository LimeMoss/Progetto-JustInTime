package com.justInTime.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazzoScarto extends Mazzo {

    private List<Carta> carteScartate;

    public MazzoScarto() {
        carteScartate = new ArrayList<>();
        aggiungiCartaCasuale();
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

    public final void aggiungiCartaCasuale(){
        Random random= new Random();
        int randomValue= random.nextInt(10);

        Carta nuovaCarta;

        if(randomValue==0){
            nuovaCarta= new Carta("Jolly");
        }else{
            nuovaCarta= new Carta(randomValue);
        }
        aggiungi(nuovaCarta);

    }

}