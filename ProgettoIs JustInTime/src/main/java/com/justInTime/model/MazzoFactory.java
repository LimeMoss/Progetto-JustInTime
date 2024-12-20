package com.justInTime.model;

/**
 * Classe factory per la creazione di istanze di {@link Mazzo} in base al tipo specificato.
 * Fornisce un metodo statico per generare diversi tipi di mazzi.
 */
public class MazzoFactory {

    /**
     * Crea un'istanza di {@link Mazzo} in base al tipo specificato.
     * 
     * @param tipo Il tipo di mazzo da creare (es. "pesca", "scarto").
     * @return Un'istanza del tipo di mazzo specificato.
     * @throws IllegalArgumentException Se il tipo di mazzo fornito non è valido.
     */
    public static Mazzo createMazzo(String tipo) {
        switch (tipo.toLowerCase()) {
            case "pesca":
                return new MazzoPesca();
            case "scarto":
                return new MazzoScarto();
            default:
                throw new IllegalArgumentException("Tipo di mazzo non valido: " + tipo);
        }
    }
}
