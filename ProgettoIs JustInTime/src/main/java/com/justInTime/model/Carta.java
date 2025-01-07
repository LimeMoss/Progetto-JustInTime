package com.justInTime.model;
/**
 * Classe che rappresenta una Carta di gioco. Ogni carta ha un tipo e un valore associato,
 * e può applicare un effetto a un giocatore in base al proprio tipo.
 */
public class Carta {
    private final String tipo;  // Tipo della carta (es. "Accelera", "Rallenta", "numerata")
    private final int valore;   // Valore della carta (solo per carte numerate)

    /**
     * Costruttore per creare una carta con un tipo specifico.
     * Per default, il valore della carta viene impostato a 99.
     *
     * @param tipo Il tipo della carta (es. "Accelera", "Rallenta").
     */
    public Carta(String tipo) {
        this.tipo = tipo;
        this.valore = 99;  
    }

    /**
     * Costruttore per creare una carta numerata con un valore specifico.
     * Il tipo della carta sarà impostato a "numerata".
     *
     * @param valore Il valore numerico della carta.
     */
    public Carta(int valore) {
        this.tipo = "numerata";
        this.valore = valore;
    }

    /**
     * Restituisce il tipo della carta.
     *
     * @return Il tipo della carta.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Restituisce il valore della carta.
     *
     * @return Il valore della carta.
     */
    public int getValore() {
        return valore;
    }

    /**
     * Applica l'effetto della carta a un giocatore.
     *
     * Se la carta è di tipo "Accelera", riduce la durata del turno del giocatore di 2 unità.
     * Se la carta è di tipo "Rallenta", aumenta la durata del turno del giocatore di 2 unità.
     * Le carte di tipo "numerata" non applicano alcun effetto.
     *
     * @param giocatore Il giocatore a cui applicare l'effetto della carta.
     */
    public void applicaEffetto(Player giocatore) {
        if (this.tipo.equals("Accelera")) {
            giocatore.setDurataTurno(giocatore.getDurataTurno() - 2);  // Riduce la durata del turno
        } else if (this.tipo.equals("Rallenta")) {
            giocatore.setDurataTurno(giocatore.getDurataTurno() + 2);  // Aumenta la durata del turno
        }
    }
}
