package com.justInTime.model;

public class MazzoFactory {
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
