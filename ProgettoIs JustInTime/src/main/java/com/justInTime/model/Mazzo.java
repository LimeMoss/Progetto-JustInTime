package com.justInTime.model;



// Definizione dell'interfaccia Mazzo
public abstract class Mazzo {

    public abstract void aggiungi(Carta componente);

    public abstract void rimuovi(Carta componente);

    public abstract Carta getCarta(int index);
}