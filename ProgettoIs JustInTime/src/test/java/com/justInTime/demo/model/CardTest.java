
package com.justInTime.demo.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.justInTime.model.Carta;
import com.justInTime.model.Player;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    private Carta cartaNumerata;
    private Carta cartaSpeciale;
    private Player giocatore;

    @BeforeEach
    public void setUp() {
    
        cartaNumerata = new Carta(5);  
        cartaSpeciale = new Carta("Accelera");  
        giocatore = new Player();  
        giocatore.setDurataTurno(10);  
    }

    @Test
    public void testCartaNumerata() {
        assertEquals("numerata", cartaNumerata.getTipo());
        assertEquals(5, cartaNumerata.getValore());
    }

    @Test
    public void testCartaSpeciale() {
        assertEquals("Accelera", cartaSpeciale.getTipo());
    }

    @Test
    public void testEffettoCartaAccelera() {
        // Verifica che l'effetto "Accelera" funzioni correttamente
        cartaSpeciale.applicaEffetto(giocatore);
        assertEquals(8, giocatore.getDurataTurno());
    }

    @Test
    public void testEffettoCartaRallenta() {
        Carta cartaRallenta = new Carta("Rallenta");
        cartaRallenta.applicaEffetto(giocatore);
        assertEquals(12, giocatore.getDurataTurno());  // Il turno del giocatore deve essere aumentato di 2 secondi
    }
    
}
