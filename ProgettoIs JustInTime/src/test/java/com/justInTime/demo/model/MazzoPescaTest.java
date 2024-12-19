import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MazzoPescaTest {

    private MazzoPesca mazzoPesca;

    @BeforeEach
    void setUp() {
        mazzoPesca = new MazzoPesca();
    }

    @Test
    void testPescaCarta() {
        // Pesca una carta dal mazzo e verifica che non sia null
        Carta carta = mazzoPesca.pescaCarta();
        assertNotNull(carta, "La carta pescata non dovrebbe essere null");

        // Verifica che il mazzo sia ridotto di una carta
        assertEquals(mazzoPesca.getCarte().size(), 35, "Il mazzo dovrebbe avere 35 carte dopo una pesca.");
    }

    @Test
    void testPescaCartaQuandoVuoto() {
        // Rimuoviamo tutte le carte dal mazzo per testare il caso in cui sia vuoto
        while (mazzoPesca.getCarte().size() > 0) {
            mazzoPesca.pescaCarta();
        }

        // Pesca una carta dal mazzo vuoto e verifica che ritorni null
        Carta carta = mazzoPesca.pescaCarta();
        assertNull(carta, "Il mazzo è vuoto, la pesca dovrebbe ritornare null");
    }

    @Test
    void testAggiungiCarta() {
        // Aggiungi una carta al mazzo
        Carta carta = new Carta(5); // Supponiamo di aggiungere una carta con valore 5
        mazzoPesca.aggiungi(carta);

        // Verifica che la carta sia stata aggiunta
        assertEquals(mazzoPesca.getCarte().size(), 37, "Il mazzo dovrebbe contenere 37 carte dopo aver aggiunto una carta");
        assertTrue(mazzoPesca.getCarte().contains(carta), "Il mazzo dovrebbe contenere la carta aggiunta");
    }

    @Test
    void testRimuoviCarta() {
        // Aggiungi una carta e poi rimuovila
        Carta carta = new Carta(5);
        mazzoPesca.aggiungi(carta);
        mazzoPesca.rimuovi(carta);

        // Verifica che la carta sia stata rimossa
        assertEquals(mazzoPesca.getCarte().size(), 36, "Il mazzo dovrebbe contenere 36 carte dopo aver rimosso una carta");
        assertFalse(mazzoPesca.getCarte().contains(carta), "Il mazzo non dovrebbe contenere la carta rimossa");
    }

    @Test
    void testGetCarta() {
        // Ottieni una carta da un indice valido
        Carta carta = mazzoPesca.getCarta(0);
        assertNotNull(carta, "La carta all'indice 0 non dovrebbe essere null");

        // Ottieni una carta da un indice invalido (out of bounds)
        Carta cartaInvalida = mazzoPesca.getCarta(100); // Un indice fuori dal range
        assertNull(cartaInvalida, "La carta all'indice 100 dovrebbe essere null, perché il mazzo ha meno di 100 carte");
    }

    @Test
    void testGeneraCartePesca() {
        // Verifica che il mazzo contenga il numero di carte previsto
        assertEquals(mazzoPesca.getCarte().size(), 54, "Il mazzo dovrebbe contenere 54 carte dopo la generazione");
    }
}
