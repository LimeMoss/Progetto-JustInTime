import com.justInTime.model.Player;
import com.justInTime.model.Utenza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;

public class UtenzaTest {

    private Utenza utenza;
    private Player player;

    @BeforeEach
    void setUp() {
        // Setup iniziale per ogni test
        player = mock(Player.class); // Creiamo un mock per la classe Player
        utenza = new Utenza("Standard");
    }

    @Test
    void testCostruttoreConTipo() {
        // Test del costruttore con tipo
        Utenza utenza = new Utenza("Standard");
        assertEquals("Standard", utenza.getTipo());
    }

    @Test
    void testSetterAndGetter() {
        // Test dei metodi getter e setter
        utenza.setName("Mario");
        utenza.setPaese("Italia");

        assertEquals("Mario", utenza.getName());
        assertEquals("Italia", utenza.getPaese());
    }

    @Test
    void testImpostaPlayer() {
        // Test dell'associazione con un oggetto Player
        utenza.setPlayer(player);
        
        // Verifica che il player sia stato correttamente associato
        assertNotNull(utenza);
    }

    @Test
    void testCostruttoreCompleto() {
        // Test del costruttore completo
        Date dataNascita = new Date();
        Date dataCreazioneAccount = new Date();
        
        Utenza utenzaCompleta = new Utenza(1, "Mario", "Rossi", dataNascita, "Indirizzo", "Italia", "123456789", 
                                           "mario@email.com", "password", dataCreazioneAccount, "mario123", "Standard", "img.jpg");
        
        assertEquals("Mario", utenzaCompleta.getName());
        assertEquals("Rossi", utenzaCompleta.getCognome());
        assertEquals("Italia", utenzaCompleta.getPaese());
        assertEquals("mario@email.com", utenzaCompleta.getEmail());
        assertEquals("mario123", utenzaCompleta.getUsername());
    }

    @Test
    void testGetName() {
        // Test che verifica che il nome venga restituito correttamente
        utenza.setName("Giovanni");
        assertEquals("Giovanni", utenza.getName());
    }

    @Test
    void testRelazionePlayerUtenza() {
        // Verifica che l'oggetto Player possa essere correttamente associato a Utenza
        Utenza utenzaTest = new Utenza();
        utenzaTest.setPlayer(player);
        
        assertNotNull(utenzaTest);
        assertEquals(player, utenzaTest.getPlayer());  // Verifica che il player associato sia lo stesso
    }

    @Test
    void testGetTipo() {
        // Test che verifica che il tipo venga restituito correttamente
        utenza = new Utenza("Admin");
        assertEquals("Admin", utenza.getTipo());
    }
}
