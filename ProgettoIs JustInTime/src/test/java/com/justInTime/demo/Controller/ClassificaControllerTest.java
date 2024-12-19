import com.justInTime.controller.ClassificaController;
import com.justInTime.model.Player;
import com.justInTime.service.ClassificaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ClassificaControllerTest {

    @Mock
    private ClassificaService classificaService;

    @InjectMocks
    private ClassificaController classificaController;

    private List<Player> players;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        players = Arrays.asList(new Player("Player1", 100), new Player("Player2", 200));
    }

    @Test
    void testGetClassifica() {
        // Mock the service call
        when(classificaService.getClassifica()).thenReturn(players);

        // Call the method to get the classifica
        List<Player> response = classificaController.getClassifica();

        // Assert the response is as expected
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Player2", response.get(0).getName()); // Assuming sorted by score
    }
}
