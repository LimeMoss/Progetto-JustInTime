import com.justInTime.model.Player;
import com.justInTime.repository.PlayerRepository;
import com.justInTime.service.ClassificaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClassificaServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private ClassificaService classificaService;

    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inizializza i mock

        // Creiamo alcuni giocatori di esempio
        player1 = new Player("Giocatore1", 100);
        player2 = new Player("Giocatore2", 200);
        player3 = new Player("Giocatore3", 150);
    }

    @Test
    void testGetClassifica() {
        // Simula il comportamento del repository per restituire una lista di giocatori
        when(playerRepository.findAllByOrderByMaxScoreDesc()).thenReturn(Arrays.asList(player2, player3, player1));

        // Chiamata al servizio
        List<Player> classifica = classificaService.getClassifica();

        // Verifica che la lista sia ordinata correttamente
        assertEquals(3, classifica.size());
        assertEquals("Giocatore2", classifica.get(0).getName());
        assertEquals("Giocatore3", classifica.get(1).getName());
        assertEquals("Giocatore1", classifica.get(2).getName());

        // Verifica che il repository sia stato chiamato una volta
        verify(playerRepository, times(1)).findAllByOrderByMaxScoreDesc();
    }

    @Test
    void testGetClassificaQuandoVuota() {
        // Simula il comportamento del repository per restituire una lista vuota
        when(playerRepository.findAllByOrderByMaxScoreDesc()).thenReturn(Arrays.asList());

        // Chiamata al servizio
        List<Player> classifica = classificaService.getClassifica();

        // Verifica che la lista sia vuota
        assertTrue(classifica.isEmpty());

        // Verifica che il repository sia stato chiamato una volta
        verify(playerRepository, times(1)).findAllByOrderByMaxScoreDesc();
    }
}
