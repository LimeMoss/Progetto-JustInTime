package com.justInTime.Service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.justInTime.model.StartGameState;
import com.justInTime.model.TurnState;
import com.justInTime.repository.PartitaRepository;
import com.justInTime.service.PartitaService;
import com.justInTime.service.PartitaConfigService;
import com.justInTime.model.PauseState;
import com.justInTime.model.Player;
import com.justInTime.model.Mazzo;
import com.justInTime.model.MazzoFactory;
import com.justInTime.model.MazzoPesca;
import com.justInTime.model.MazzoScarto;
import com.justInTime.model.Carta;
import com.justInTime.model.EndGameState;
import com.justInTime.model.Partita;

import java.util.Arrays;
import java.util.ArrayList;

public class GameStateTest {

    @Mock
    private PartitaService partitaService;

    @Mock
    private PartitaRepository partitaRepository;
    
    @Mock
    private PartitaConfigService partitaConfigService;

    @InjectMocks
    private StartGameState startGameState;

    @InjectMocks
    private TurnState turnState;

    @InjectMocks
    private PauseState pauseState;

    @InjectMocks
    private EndGameState endGameState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class StartGameStateTests {
        @Test
        void testStartGameExecution() {
            Partita partita = new Partita();
            startGameState.execute(partita);
            
            verify(partitaService).distribuisciCarteIniziali(partita);
        verify(partitaService).setGameState(eq(partita), any(TurnState.class));

            assertEquals(0, partita.getIndiceGiocatoreCorrente());
        }
    }

    @Nested
    class TurnStateTests {
        @Test
        void testTurnWithFinishedPlayer() {
            Partita partita = new Partita();
            Player player = new Player();
            player.setDurataTurno(10);
            player.setTurnoInPausa(true);
            
            partita.setGiocatori(Arrays.asList(player));
            partita.setIndiceGiocatoreCorrente(0);
            
            turnState.execute(partita);
            verify(partitaService).setGameState(eq(partita), any(PauseState.class));
        }

        @Test
        void testTurnTimeout() {
            Partita partita = new Partita();
            Player player = new Player();
            player.setDurataTurno(1);
            player.setTurnoInPausa(false);
            
            partita.setGiocatori(Arrays.asList(player));
            partita.setIndiceGiocatoreCorrente(0);
            
            turnState.execute(partita);
            verify(partitaService).setGameState(eq(partita), any(PauseState.class));
            assertEquals(0, player.getDurataTurno());
        }

        @Test
        void testMultiplePlayerTurns() {

            Player player1 = new Player();
            Player player2 = new Player();
            player1.setName("Ciro");
            player2.setName("Moss");
        
           
            partitaConfigService.aggiungiGiocatoreConfigNOLOGIN(player1);
            partitaConfigService.aggiungiGiocatoreConfigNOLOGIN(player2);

            Partita mockPartita = new Partita();
            mockPartita.setGiocatori(new ArrayList<>(Arrays.asList(player1, player2)));
            mockPartita.setGameState(new StartGameState());
            mockPartita.setMazzoNormale(new MazzoPesca()); 
            mockPartita.setMazzoScarto(new MazzoScarto());
            mockPartita.setId(1L);

            when(partitaConfigService.creaPartita()).thenReturn(mockPartita);

            Partita partita = mockPartita;
            assertNotNull(partita, "La partita non è stata creata correttamente");
    

            for (int i=0; i<60; i++) {
                assertNotNull(partita.getMazzoNormale().getCarta(i).getValore(), "Il mazzo normale non può essere null");
            
                System.out.println("Mazzo normale: " + partita.getMazzoNormale().getCarta(i).getValore());
            }
            player1.setDurataTurno(2);
            player2.setDurataTurno(2);
        
            partita.setGiocatori(Arrays.asList(player1, player2));
            partita.setIndiceGiocatoreCorrente(0);
     
            for (Player giocatore : partita.getGiocatori()) {
                for (int i = 0; i < 5; i++) {
                  giocatore.getMano().add(partita.getMazzoNormale().getCarta(i));
                }
            }
            // Inizio partita
            
    

            for (Player x : partita.getGiocatori()) {
                assertNotNull(x.getName(), "Il nome del giocatore non può essere null");
                assertNotNull(x.getMano(), "La mano del giocatore non può essere null");
                System.out.println("Giocatore: " + x.getName() + " Mano: " + x.getMano());
            }
    
            verify(partitaService).setGameState(eq(partita), any(PauseState.class));
        
            // Preparazione del giocatore
            pauseState.playerReady();
            pauseState.execute(partita);
            verify(partitaService).passaAlProssimoGiocatore(partita);
            assertEquals(1, partita.getIndiceGiocatoreCorrente());
        
            // Esecuzione turno successivo
            turnState.execute(partita);
            verify(partitaService, times(2)).setGameState(eq(partita), any(PauseState.class));
        }
    }

    @Nested
    class PauseStateTests {
        @Test
        void testGameOverCondition() {
            Partita partita = new Partita();
            Player player = new Player();
            partita.setGiocatori(Arrays.asList(player));
            
            pauseState.playerReady();
            pauseState.execute(partita);
            
            verify(partitaService).passaAlProssimoGiocatore(partita);
            verify(partitaService).setGameState(eq(partita), any(EndGameState.class));
        }

        @Test
        void testGameContinuation() {
            Partita partita = new Partita();
            Player player = new Player();
          
            partita.setGiocatori(Arrays.asList(player));
            
            pauseState.playerReady();
            pauseState.execute(partita);
            
            verify(partitaService).passaAlProssimoGiocatore(partita);
            verify(partitaService).setGameState(eq(partita), any(TurnState.class));

    

        }
    }

    @Nested
    class EndGameStateTests {
        @Test
        void testGameTermination() {
            Partita partita = new Partita();
            partita.setId(1L);
            
            endGameState.execute(partita);
            verify(partitaService).terminaPartita(1L);
        }
    }

    @Test
    void testFullGameCycle() {
        Partita partita = new Partita();
        Player player1 = new Player();
        Player player2 = new Player();
 
        partita.setGiocatori(Arrays.asList(player1, player2));
        partita.setIndiceGiocatoreCorrente(0);
        
        // Start game
        startGameState.execute(partita);
        verify(partitaService).distribuisciCarteIniziali(partita);
        
        // First player turn
        turnState.execute(partita);
        pauseState.playerReady();
        pauseState.execute(partita);
        
        // Second player turn and win
        player2.getMano().clear();
        turnState.execute(partita);
        pauseState.playerReady();
        pauseState.execute(partita);
        
        // Verify end game
        verify(partitaService).setGameState(eq(partita), any(EndGameState.class));
    }
}
