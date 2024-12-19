
package com.justInTime.model;

public class Carta  {
    private String tipo;  
    private int valore; 

    public Carta(String tipo) {
        this.tipo = tipo;
        this.valore = 99;  
    }

    public Carta(int valore) {
        this.tipo = "numerata";
        this.valore = valore;
    }



    // Getter e Setter
    public String getTipo() {
        return tipo;
    }

    public int getValore() {
        return valore;
    }


    //TODO// controllare in caso di concorrenze se aumenta tempo di piu' partite, magari agigungere check su partita
    public void applicaEffetto(Player giocatore) {
        if (this.tipo.equals("Accelera")) {
            // Riduce il tempo del prossimo giocatore di 2 secondi
            giocatore.setDurataTurno(giocatore.getDurataTurno() - 2);  
        } if (this.tipo.equals("Rallenta")) {
            // Aumenta il tempo del turno corrente di 2 secondi
            giocatore.setDurataTurno(giocatore.getDurataTurno() + 2);  
        }
    }
    
}

