import com.justInTime.controller.MazzoController;
import com.justInTime.model.Carta;
import com.justInTime.service.MazzoPescaService;
import com.justInTime.service.MazzoScartoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MazzoControllerTest {

    @Mock
    private MazzoPescaService mazzoPescaService;

    @Mock
    private MazzoScartoService mazzoScartoService;

    @InjectMocks
    private MazzoController mazzoController;

    private Carta cartaPesca;
    private Carta cartaScarto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        cartaPesca = new Carta("Cuori", "Asso");
        cartaScarto = new Carta("Fiori", "Re");
    }

    @Test
    void testPescaCarta_Success() {
        // Mock the service to return a carta
        when(mazzoPescaService.pescaCarta()).thenReturn(cartaPesca);

        // Call the controller method
        ResponseEntity<Carta> response = mazzoController.pescaCarta();

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartaPesca, response.getBody());
    }

    @Test
    void testPescaCarta_NotFound() {
        // Mock the service to return null (mazzo vuoto)
        when(mazzoPescaService.pescaCarta()).thenReturn(null);

        // Call the controller method
        ResponseEntity<Carta> response = mazzoController.pescaCarta();

        // Assert response status is NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUltimaCartaScartata_Success() {
        // Mock the service to return a carta scartata
        when(mazzoScartoService.ultimaCartaScartata()).thenReturn(cartaScarto);

        // Call the controller method
        ResponseEntity<Carta> response = mazzoController.ultimaCartaScartata();

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartaScarto, response.getBody());
    }

    @Test
    void testUltimaCartaScartata_NotFound() {
        // Mock the service to return null (mazzo degli scarti vuoto)
        when(mazzoScartoService.ultimaCartaScartata()).thenReturn(null);

        // Call the controller method
        ResponseEntity<Carta> response = mazzoController.ultimaCartaScartata();

        // Assert response status is NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testScartaCarta() {
        // Mock the service to successfully add the card
        doNothing().when(mazzoScartoService).aggiungiCarta(cartaPesca);

        // Call the controller method
        ResponseEntity<Void> response = mazzoController.scartaCarta(cartaPesca);

        // Assert response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAggiungiCartaPesca() {
        // Mock the service to successfully add the card
        doNothing().when(mazzoPescaService).aggiungiCarta(cartaPesca);

        // Call the controller method
        ResponseEntity<Void> response = mazzoController.aggiungiCartaPesca(cartaPesca);

        // Assert response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRimuoviCartaPesca() {
        // Mock the service to successfully remove the card
        doNothing().when(mazzoPescaService).rimuoviCarta(cartaPesca);

        // Call the controller method
        ResponseEntity<Void> response = mazzoController.rimuoviCartaPesca(cartaPesca);

        // Assert response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRimuoviCartaScarto() {
        // Mock the service to successfully remove the card
        doNothing().when(mazzoScartoService).rimuoviCarta(cartaScarto);

        // Call the controller method
        ResponseEntity<Void> response = mazzoController.rimuoviCartaScarto(cartaScarto);

        // Assert response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetCartaPesca_Success() {
        // Mock the service to return a carta at a specific index
        when(mazzoPescaService.getCarta(0)).thenReturn(cartaPesca);

        // Call the controller method
        ResponseEntity<Carta> response = mazzoController.getCartaPesca(0);

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartaPesca, response.getBody());
    }

    @Test
    void testGetCartaPesca_NotFound() {
        // Mock the service to return null (invalid index)
        when(mazzoPescaService.getCarta(99)).thenReturn(null);

        // Call the controller method
        ResponseEntity<Carta> response = mazzoController.getCartaPesca(99);

        // Assert response status is NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetCartaScarto_Success() {
        // Mock the service to return a carta at a specific index
        when(mazzoScartoService.getCarta(0)).thenReturn(cartaScarto);

        // Call the controller method
        ResponseEntity<Carta> response = mazzoController.getCartaScarto(0);

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartaScarto, response.getBody());
    }

    @Test
    void testGetCartaScarto_NotFound() {
        // Mock the service to return null (invalid index)
        when(mazzoScartoService.getCarta(99)).thenReturn(null);

        // Call the controller method
        ResponseEntity<Carta> response = mazzoController.getCartaScarto(99);

        // Assert response status is NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
