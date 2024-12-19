import com.justInTime.controller.PlayerController;
import com.justInTime.model.Carta;
import com.justInTime.model.Player;
import com.justInTime.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    private Player player;
    private Carta carta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize test data
        player = new Player(1L, "Giocatore1", 10);
        carta = new Carta("Cuori", "Asso");
    }

    @Test
    void testCreaGiocatore() {
        // Mock the service to return a player
        when(playerService.creaGiocatore("Giocatore1", 10)).thenReturn(player);

        // Call the controller method
        ResponseEntity<Player> response = playerController.creaGiocatore("Giocatore1", 10);

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(player, response.getBody());
    }

    @Test
    void testTrovaGiocatore() {
        // Mock the service to return a player by ID
        when(playerService.trovaGiocatore(1L)).thenReturn(player);

        // Call the controller method
        ResponseEntity<Player> response = playerController.trovaGiocatore(1L);

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(player, response.getBody());
    }

    @Test
    void testTrovaTuttiGiocatori() {
        // Mock the service to return a list of players
        when(playerService.trovaTuttiGiocatori()).thenReturn(List.of(player));

        // Call the controller method
        ResponseEntity<List<Player>> response = playerController.trovaTuttiGiocatori();

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(player));
    }

    @Test
    void testAggiornaNomeGiocatore() {
        // Mock the service to update the player's name
        when(playerService.aggiornaNomeGiocatore(1L, "NuovoNome")).thenReturn(new Player(1L, "NuovoNome", 10));

        // Call the controller method
        ResponseEntity<Player> response = playerController.aggiornaNomeGiocatore(1L, "NuovoNome");

        // Assert response status and updated name
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("NuovoNome", response.getBody().getName());
    }

    @Test
    void testAggiungiCartaAllaMano() {
        // Mock the service to add a card to the player's hand
        when(playerService.aggiungiCartaAllaMano(1L, carta)).thenReturn(player);

        // Call the controller method
        ResponseEntity<Player> response = playerController.aggiungiCartaAllaMano(1L, carta);

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(player, response.getBody());
    }

    @Test
    void testRimuoviCartaDallaMano() {
        // Mock the service to remove a card from the player's hand
        when(playerService.rimuoviCartaDallaMano(1L, carta)).thenReturn(player);

        // Call the controller method
        ResponseEntity<Player> response = playerController.rimuoviCartaDallaMano(1L, carta);

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(player, response.getBody());
    }

    @Test
    void testIncrementaMaxScore() {
        // Mock the service to increment max score
        when(playerService.incrementaMaxScore(1L)).thenReturn(new Player(1L, "Giocatore1", 11));

        // Call the controller method
        ResponseEntity<Player> response = playerController.incrementaMaxScore(1L);

        // Assert response status and incremented score
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(11, response.getBody().getMaxScore());
    }
}
