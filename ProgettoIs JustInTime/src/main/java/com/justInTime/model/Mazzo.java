package com.justInTime.model;




public abstract class Mazzo {

    public abstract void aggiungi(Carta componente);

    public abstract void rimuovi(Carta componente);

    public abstract Carta getCarta(int index);
}