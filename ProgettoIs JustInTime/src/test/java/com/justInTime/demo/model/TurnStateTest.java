import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.service.PartitaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)  // Permette di usare Mockito con JUnit 5
public class TurnStateTest {

    @InjectMocks
    private TurnState turnState;  // Classe che stiamo testando

    @Mock
    private PartitaService partitaService;  // Mock di PartitaService

    @Mock
    private Partita partita;  // Mock di Partita

    @Mock
    private Player giocatoreCorrente;  // Mock di Player

    @BeforeEach
    void setUp() {
        // Impostare i comportamenti dei mock prima di ogni test
        when(partita.getGiocatori()).thenReturn(java.util.Arrays.asList(giocatoreCorrente));
        when(partita.getIndiceGiocatoreCorrente()).thenReturn(0);
        when(giocatoreCorrente.getName()).thenReturn("Giocatore1");
        when(giocatoreCorrente.getDurataTurno()).thenReturn(5);  // Impostiamo 5 secondi come durata del turno
    }

    @Test
    void testExecuteConTurnoCompleto() throws InterruptedException {
        // Eseguiamo il metodo execute
        turnState.execute(partita);

        // Verifica che il metodo passaAlProssimoGiocatore sia stato chiamato
        verify(partitaService, times(1)).passaAlProssimoGiocatore(partita);

        // Verifica che il tempo rimanente sia stato decrementato correttamente
        verify(giocatoreCorrente, times(5)).setDurataTurno(anyInt());
    }

    @Test
    void testExecuteConTurnoInPausa() throws InterruptedException {
        // Simuliamo che il giocatore sia in pausa
        when(giocatoreCorrente.isTurnoInPausa()).thenReturn(true);

        // Eseguiamo il metodo execute
        turnState.execute(partita);

        // Verifica che il metodo passaAlProssimoGiocatore sia stato chiamato anche in caso di pausa
        verify(partitaService, times(1)).passaAlProssimoGiocatore(partita);
    }

    @Test
    void testExecuteConPartitaFinita() throws InterruptedException {
        // Simula che la partita sia finita
        when(partita.getGameState()).thenReturn(new EndGameState());

        // Eseguiamo il metodo execute
        turnState.execute(partita);

        // Verifica che il metodo passaAlProssimoGiocatore non venga chiamato se il gioco è finito
        verify(partitaService, never()).passaAlProssimoGiocatore(partita);
    }
}
