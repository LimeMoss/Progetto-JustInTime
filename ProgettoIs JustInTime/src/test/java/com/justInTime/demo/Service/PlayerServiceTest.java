package com.justInTime.demo.Service;

import com.justInTime.model.Carta;
import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.repository.PartitaRepository;
import com.justInTime.repository.PlayerRepository;
import com.justInTime.service.PlayerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PartitaRepository partitaRepository;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void creaGiocatore_shouldSavePlayer() {
        Player player = new Player("Mario", 100);
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player result = playerService.creaGiocatore("Mario", 100);

        assertNotNull(result);
        assertEquals("Mario", result.getName());
        assertEquals(100, result.getMaxScore());
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void trovaGiocatore_shouldReturnPlayerIfFound() {
        Player player = new Player("Luigi", 200);
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        Player result = playerService.trovaGiocatore(1L);

        assertNotNull(result);
        assertEquals("Luigi", result.getName());
        assertEquals(200, result.getMaxScore());
        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    void trovaGiocatore_shouldThrowExceptionIfNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> playerService.trovaGiocatore(1L));

        assertEquals("Giocatore non trovato.", exception.getMessage());
        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    void trovaTuttiGiocatori_shouldReturnListOfPlayers() {
        List<Player> players = List.of(new Player("Mario", 100), new Player("Luigi", 200));
        when(playerRepository.findAll()).thenReturn(players);

        List<Player> result = playerService.trovaTuttiGiocatori();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    void aggiornaNomeGiocatore_shouldUpdateName() {
        Player player = new Player("Mario", 100);
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player result = playerService.aggiornaNomeGiocatore(1L, "Luigi");

        assertNotNull(result);
        assertEquals("Luigi", result.getName());
        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void aggiungiCartaAllaMano_shouldAddCard() {
        Player player = new Player("Mario", 100);
        Carta carta = new Carta("Asso di Cuori");
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player result = playerService.aggiungiCartaAllaMano(1L, carta);

        assertNotNull(result);
        assertTrue(result.getMano().contains(carta));
        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void deletePlayer_shouldRemovePlayerFromGamesAndDeletePlayer() {
        Player player = new Player("Mario", 100);
        Partita partita = new Partita();
        
        partita.getGiocatori().add(player);
        player.getPartite().add(partita);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        playerService.deletePlayer(1L);

        assertFalse(partita.getGiocatori().contains(player));
        verify(partitaRepository, times(1)).save(partita);
        verify(playerRepository, times(1)).deleteById(1L);
    }
    @Test
void rimuoviCartaDallaMano_shouldRemoveCardFromPlayerHand() {
    Player player = new Player("Mario", 100);
    Carta carta = new Carta("Asso di Cuori");
    player.aggiungiCartaAllaMano(carta); // Aggiungi la carta alla mano del giocatore

    when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
    when(playerRepository.save(any(Player.class))).thenReturn(player);

    Player result = playerService.rimuoviCartaDallaMano(1L, carta);

    assertNotNull(result);
    assertFalse(result.getMano().contains(carta)); // Assicurati che la carta sia stata rimossa
    verify(playerRepository, times(1)).findById(1L);
    verify(playerRepository, times(1)).save(player);
}
@Test
void incrementaMaxScore_shouldIncreasePlayerMaxScore() {
    Player player = new Player("Mario", 100);

    when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
    when(playerRepository.save(any(Player.class))).thenReturn(player);

    Player result = playerService.incrementaMaxScore(1L);

    assertNotNull(result);
    assertEquals(101, result.getMaxScore()); // Assumi che `IncreaseMaxScore` aumenti di 1 il punteggio massimo
    verify(playerRepository, times(1)).findById(1L);
    verify(playerRepository, times(1)).save(player);
}


}
