package com.justInTime.demo.Service;


import com.justInTime.model.Carta;

import com.justInTime.service.MazzoPescaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazzoPescaServiceTest {

    private MazzoPescaService mazzoPescaService;

    @BeforeEach
    void setUp() {
        mazzoPescaService = new MazzoPescaService();
    }

    @Test
    void testAggiungiCarta() {
  
        Carta carta = new Carta(5);
        mazzoPescaService.aggiungiCarta(carta);
        
        // Verifica che la carta sia stata aggiunta
        assertNotNull(mazzoPescaService.getCarta(0));
        assertEquals(5, mazzoPescaService.getCarta(0).getValore());
    }

    @Test
    void testRimuoviCarta() {
         Carta inizio = mazzoPescaService.getCarta(0);
        Carta carta = new Carta(5);
        mazzoPescaService.aggiungiCarta(carta);
        mazzoPescaService.rimuoviCarta(carta);
        
        // Verifica che la carta sia stata rimossa
        assertEquals(inizio, mazzoPescaService.getCarta(0));
    }

    @Test
    void testPescaCarta() {

        Carta carta1 = new Carta(5);
        Carta carta2 = new Carta(10);
        mazzoPescaService.aggiungiCarta(carta1);
        mazzoPescaService.aggiungiCarta(carta2);
        
        // Verifica che la carta pescata sia la prima e che venga rimossa dal mazzo
        Carta cartaPesca = mazzoPescaService.pescaCarta();
        assertNotNull(cartaPesca);
        assertEquals(10, cartaPesca.getValore());
        
        // Verifica che la carta sia stata rimossa dal mazzo
        assertEquals(carta1 ,mazzoPescaService.getCarta(0));
    }
}
