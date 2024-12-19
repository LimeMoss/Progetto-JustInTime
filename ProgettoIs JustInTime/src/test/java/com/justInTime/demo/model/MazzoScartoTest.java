import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazzoScartoTest {

    private MazzoScarto mazzoScarto;
    private Carta carta;

    @BeforeEach
    void setUp() {
        mazzoScarto = new MazzoScarto();
        carta = new Carta(5); // Carta con valore 5
    }

    @Test
    void testAggiungiCarta() {
        mazzoScarto.aggiungi(carta);

        // Verifica che la carta sia stata aggiunta
        assertEquals(1, mazzoScarto.getCarteScartate().size(), "Il mazzo di scarto dovrebbe contenere 1 carta.");
        assertTrue(mazzoScarto.getCarteScartate().contains(carta), "La carta dovrebbe essere presente nel mazzo di scarto.");
    }

    @Test
    void testRimuoviCarta() {
        mazzoScarto.aggiungi(carta);
        mazzoScarto.rimuovi(carta);

        // Verifica che la carta sia stata rimossa
        assertEquals(0, mazzoScarto.getCarteScartate().size(), "Il mazzo di scarto dovrebbe essere vuoto dopo aver rimosso la carta.");
        assertFalse(mazzoScarto.getCarteScartate().contains(carta), "La carta non dovrebbe essere presente nel mazzo di scarto.");
    }

    @Test
    void testUltimaCartaScartata() {
        assertNull(mazzoScarto.ultimaCartaScartata(), "Il mazzo di scarto dovrebbe essere vuoto all'inizio.");

        mazzoScarto.aggiungi(carta);

        // Verifica che la carta aggiunta sia l'ultima scartata
        assertEquals(carta, mazzoScarto.ultimaCartaScartata(), "L'ultima carta scartata dovrebbe essere la carta aggiunta.");
    }

    @Test
    void testUltimaCartaScartataQuandoVuoto() {
        // Verifica che il metodo ritorni null quando il mazzo di scarto è vuoto
        assertNull(mazzoScarto.ultimaCartaScartata(), "Il mazzo di scarto è vuoto, quindi l'ultima carta scartata dovrebbe essere null.");
    }

    @Test
    void testGetCarta() {
        mazzoScarto.aggiungi(carta);

        // Verifica che la carta esista all'indice 0
        assertEquals(carta, mazzoScarto.getCarta(0), "La carta all'indice 0 dovrebbe essere la carta aggiunta.");

        // Verifica che il metodo ritorni null per un indice fuori dai limiti
        assertNull(mazzoScarto.getCarta(100), "L'indice 100 è fuori dai limiti, quindi la carta dovrebbe essere null.");
    }
}
