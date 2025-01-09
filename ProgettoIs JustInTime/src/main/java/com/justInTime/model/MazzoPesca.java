
package com.justInTime.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




public class MazzoPesca extends Mazzo {

    private final List<Carta> carte;

    public MazzoPesca() {
        carte = new ArrayList<>();
        generaCartePesca();
        Collections.shuffle(carte);
    }

    @Override
    public void aggiungi(Carta componente) {
        carte.addFirst(componente);
    }

    @Override
    public void rimuovi(Carta componente) {
        carte.remove(componente);
    }

    @Override
    public Carta getCarta(int index) {
        if (index < carte.size()) {
            return carte.get(index);
        }
        return null;
    }

    public Carta pescaCarta() {
        if (!carte.isEmpty()) {
            return carte.remove(0);
        }
        return null;
    }

    private void generaCartePesca() {
        for (int i = 0; i < 6; i++) {
            for (int valore = 1; valore <= 9; valore++) {
                carte.add(new Carta(valore));
            }
        }

        for (int i = 0; i < 6; i++) {
            carte.add(new Carta("Accelera"));
            carte.add(new Carta("Rallenta"));
        }

        for (int i = 0; i < 6; i++) {
            carte.add(new Carta("Jolly"));
        }
    }

    public int getSize(){
        return carte.size();
    }

    public boolean searchCard(Carta carta){
        return carte.contains(carta);
    }

    
}
