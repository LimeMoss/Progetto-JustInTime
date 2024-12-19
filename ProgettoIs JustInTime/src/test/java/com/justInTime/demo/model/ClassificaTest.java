import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassificaTest {

    private Classifica classifica;
    private Player mockPlayer1;
    private Player mockPlayer2;

    @BeforeEach
    void setUp() {
        // Initialize Classifica
        classifica = new Classifica();

        // Mock Player objects
        mockPlayer1 = mock(Player.class);
        mockPlayer2 = mock(Player.class);
    }

    @Test
    void testSetAndGetId() {
        // Set ID
        Long expectedId = 1L;
        classifica.setId(expectedId);

        // Verify
        assertEquals(expectedId, classifica.getId(), "The ID should match the set value");
    }

    @Test
    void testSetAndGetGiocatori() {
        // Create mock list of players
        List<Player> mockPlayers = new ArrayList<>();
        mockPlayers.add(mockPlayer1);
        mockPlayers.add(mockPlayer2);

        // Set players list
        classifica.setGiocatori(mockPlayers);

        // Get players list and verify
        List<Player> retrievedPlayers = classifica.getGiocatori();
        assertNotNull(retrievedPlayers, "The players list should not be null");
        assertEquals(2, retrievedPlayers.size(), "The players list size should match");
        assertTrue(retrievedPlayers.contains(mockPlayer1), "The list should contain mockPlayer1");
        assertTrue(retrievedPlayers.contains(mockPlayer2), "The list should contain mockPlayer2");
    }

    @Test
    void testEmptyGiocatoriList() {
        // Set empty list of players
        List<Player> emptyList = new ArrayList<>();
        classifica.setGiocatori(emptyList);

        // Verify
        assertNotNull(classifica.getGiocatori(), "The players list should not be null");
        assertTrue(classifica.getGiocatori().isEmpty(), "The players list should be empty");
    }

    @Test
    void testInteractionWithMockedPlayers() {
        // Add mocked players to list
        List<Player> mockPlayers = new ArrayList<>();
        mockPlayers.add(mockPlayer1);
        mockPlayers.add(mockPlayer2);
        classifica.setGiocatori(mockPlayers);

        // Simulate behavior of mock players
        when(mockPlayer1.getName()).thenReturn("Player1");
        when(mockPlayer2.getName()).thenReturn("Player2");

        // Verify interactions
        assertEquals("Player1", mockPlayer1.getName(), "Player1's name should match the mocked value");
        assertEquals("Player2", mockPlayer2.getName(), "Player2's name should match the mocked value");

        verify(mockPlayer1, times(1)).getName();
        verify(mockPlayer2, times(1)).getName();
    }
}
