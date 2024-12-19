import com.justInTime.model.Carta;
import com.justInTime.model.Player;
import com.justInTime.model.Utenza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Carta carta;
    private Utenza utenza;

    @BeforeEach
    void setUp() {
        // Initialize test data
        player = new Player("Giocatore1", 10);
        carta = new Carta("Cuori", "Asso");
        utenza = new Utenza(1L, "user1", "password1");
    }

    @Test
    void testAggiungiCartaAllaMano() {
        // Add carta to player's hand
        player.aggiungiCartaAllaMano(carta);

        // Assert the carta is added to the player's hand
        assertTrue(player.getMano().contains(carta));
    }

    @Test
    void testRimuoviCartaDallaMano() {
        // Add carta to player's hand
        player.aggiungiCartaAllaMano(carta);

        // Remove carta from player's hand
        boolean removed = player.rimuoviCartaDallaMano(carta);

        // Assert the carta was removed
        assertTrue(removed);
        assertFalse(player.getMano().contains(carta));
    }

    @Test
    void testIncreaseMaxScore() {
        int initialMaxScore = player.getMaxScore();

        // Increase player's max score
        player.increaseMaxScore();

        // Assert max score is increased by 1
        assertEquals(initialMaxScore + 1, player.getMaxScore());
    }

    @Test
    void testAssociaUtenza() {
        // Associate player with utenza
        player.associaUtenza(utenza);

        // Assert the player has the same paese as the utenza
        assertEquals(utenza.getPaese(), player.getPaese());
    }

    @Test
    void testAssociaUtenzaNull() {
        // Associate player with null utenza
        player.associaUtenza(null);

        // Assert the player's paese is not set when utenza is null
        assertNull(player.getPaese());
    }

    @Test
    void testCostruttore() {
        // Assert the player is initialized with the correct name and maxScore
        assertEquals("Giocatore1", player.getName());
        assertEquals(10, player.getMaxScore());
        assertNotNull(player.getMano());  // Assert the hand is initialized
    }

    @Test
    void testGetterAndSetter() {
        // Test getter and setter for paese
        player.setPaese("Italia");
        assertEquals("Italia", player.getPaese());
    }
}
