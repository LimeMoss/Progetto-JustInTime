import com.justInTime.controller.UtenzaController;
import com.justInTime.model.Utenza;
import com.justInTime.service.UtenzaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UtenzaControllerTest {

    @Mock
    private UtenzaService utenzaService;

    @InjectMocks
    private UtenzaController utenzaController;

    private Utenza utenza;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize test data
        utenza = new Utenza(1L, "user1", "password1");
    }

    @Test
    void testCreaUtenza() {
        // Mock the service to return an utenza
        when(utenzaService.creaUtenza(utenza)).thenReturn(utenza);

        // Call the controller method
        ResponseEntity<Utenza> response = utenzaController.creaUtenza(utenza);

        // Assert response status and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(utenza, response.getBody());
    }

    @Test
    void testTrovaUtenza() {
        // Mock the service to return an utenza by ID
        when(utenzaService.trovaUtenza(1L)).thenReturn(utenza);

        // Call the controller method
        ResponseEntity<Utenza> response = utenzaController.trovaUtenza(1L);

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(utenza, response.getBody());
    }

    @Test
    void testTrovaTutteUtenze() {
        // Mock the service to return a list of utenze
        when(utenzaService.trovaTutteUtenze()).thenReturn(List.of(utenza));

        // Call the controller method
        ResponseEntity<List<Utenza>> response = utenzaController.trovaTutteUtenze();

        // Assert response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(utenza));
    }

    @Test
    void testAggiornaUtenza() {
        // Mock the service to update the utenza
        when(utenzaService.aggiornaUtenza(1L, utenza)).thenReturn(utenza);

        // Call the controller method
        ResponseEntity<Utenza> response = utenzaController.aggiornaUtenza(1L, utenza);

        // Assert response status and updated utenza
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(utenza, response.getBody());
    }

    @Test
    void testEliminaUtenza() {
        // Mock the service to delete the utenza
        doNothing().when(utenzaService).eliminaUtenza(1L);

        // Call the controller method
        ResponseEntity<Void> response = utenzaController.eliminaUtenza(1L);

        // Assert response status is NO_CONTENT
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
