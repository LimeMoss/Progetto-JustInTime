package com.justInTime.demo.Service;

import com.justInTime.model.Carta;

import com.justInTime.service.MazzoScartoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazzoScartoServiceTest {

    private MazzoScartoService mazzoScartoService;

    @BeforeEach
    void setUp() {
        mazzoScartoService = new MazzoScartoService();
    }

    @Test
    void testAggiungiCarta() {
        Carta carta = new Carta(5);
        mazzoScartoService.aggiungiCarta(carta);
        
        // Verifica che la carta sia stata aggiunta
        assertNotNull(mazzoScartoService.getCarta(0));
        assertEquals(5, mazzoScartoService.getCarta(0).getValore());
    }

    @Test
    void testRimuoviCarta() {
        Carta carta = new Carta(5);
        mazzoScartoService.aggiungiCarta(carta);
        mazzoScartoService.rimuoviCarta(carta);
        
        // Verifica che la carta sia stata rimossa
        assertNull(mazzoScartoService.getCarta(0));
    }

    @Test
    void testUltimaCartaScartata() {
        Carta carta1 = new Carta(5);
        Carta carta2 = new Carta(10);
        mazzoScartoService.aggiungiCarta(carta1);
        mazzoScartoService.aggiungiCarta(carta2);
        
        // Verifica che l'ultima carta scartata sia quella corretta
        assertEquals(10, mazzoScartoService.ultimaCartaScartata().getValore());
    }
}
