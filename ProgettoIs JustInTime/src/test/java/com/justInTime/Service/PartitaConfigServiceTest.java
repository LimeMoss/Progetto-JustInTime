package com.justInTime.Service;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.servlet.http.HttpSession;
import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.model.Utente;
import com.justInTime.repository.PartitaRepository;
import com.justInTime.service.PartitaConfigService;
import com.justInTime.service.PlayerService;
import com.justInTime.service.UtenzaService;


@ExtendWith(MockitoExtension.class)
public class PartitaConfigServiceTest{

    @Mock
    private PartitaRepository partitaRepository;

    @Mock
    private UtenzaService utenzaService;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PartitaConfigService partitaConfigService;


    //TC 3.1_1
    @Test
    public void AggiungiGiocatoreConfig_Success(){

        String usernameOrEmail = "user123";
        String password = "password123";

        Utente utente = mock(Utente.class);
        when(utente.getId()).thenReturn(1L);

        Player player = new Player();
        player.setUtente(utente);

        
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(session.getAttribute("SessionListener")).thenReturn(true); 


        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);
        when(playerService.trovaGiocatore(1L)).thenReturn(player);

        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);

        List<String> players = partitaConfigService.getGiocatoriInConfigurazione();
        assertEquals(1, players.size());
        verify(utenzaService).login(usernameOrEmail, password);
        verify(playerService).trovaGiocatore(1L);
        verify(utente).getId();

    }

    //TC 3.1_2
    @Test
    public void AggiungiGiocatoreConfig_UtenteNonEsiste(){

        String usernameOrEmail = "nonexistent@example.com";
        String password = "password123";
        HttpSession session = mock(HttpSession.class);

        when(utenzaService.login(usernameOrEmail, password)).thenReturn(null);
        when(session.getAttribute("utente")).thenReturn(null);
        Exception e= assertThrows(IllegalArgumentException.class, () -> partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password));
    
        assertEquals("Credenziali non valide.", e.getMessage());

        verify(utenzaService).login(usernameOrEmail, password);
        verifyNoInteractions(playerService);

    }

    //TC 3.1_3
    @Test
    public void AggiungiGiocatoreConfig_PasswordNonCorrisponde(){

    String usernameOrEmail = "user1";
    String password = "wrongPassword";

    HttpSession session = mock(HttpSession.class);

    when(utenzaService.login(usernameOrEmail, password)).thenReturn(null);
    when(session.getAttribute("utente")).thenReturn(null);


    Exception e= assertThrows(IllegalArgumentException.class, () -> partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password));

    assertEquals("Credenziali non valide.", e.getMessage());

    verify(utenzaService).login(usernameOrEmail, password);
    verifyNoInteractions(playerService);

    }
    //TC 3.1_4
    @Test
    void AggiungiGiocatoreConfig_NoUsernameOrEmail() {
    
    String usernameOrEmail = null; 
    String password = "password1";
    
    HttpSession session = mock(HttpSession.class);
    when(session.getAttribute("utente")).thenReturn(mock(Utente.class));

    Exception e = assertThrows(IllegalArgumentException.class, () -> partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password, session));
    assertEquals("Credenziali non valide.", e.getMessage());
    }

    //TC 3.1_5
    @Test
    void AggiungiGiocatoreConfig_NoPassword() {

    String usernameOrEmail = "user1";
    String password = null;
     HttpSession session = mock(HttpSession.class);
    when(session.getAttribute("utente")).thenReturn(mock(Utente.class));

    Exception e = assertThrows(IllegalArgumentException.class, () -> partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password));
    assertEquals("Credenziali non valide.", e.getMessage());

    }

    //TC 3.1.6
     @Test
    public void AggiungiGiocatoreConfig_MaxGiocatoriRaggiunto() {
    String usernameOrEmail = "user1";
    String password = "password1";

    for (int i = 1; i <= 4; i++) {
        Utente utente = mock(Utente.class);
        when(utente.getId()).thenReturn((long) i);
        when(utente.getEmail()).thenReturn("user" + i);
        Player player = new Player();
        player.setUtente(utente);
        when(utenzaService.login("user" + i, password)).thenReturn(utente);
        when(playerService.trovaGiocatore(i)).thenReturn(player);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(session.getAttribute("SessionListener")).thenReturn(true);

        partitaConfigService.aggiungiGiocatoreConfig("user" + i, password, session);
    }

    
    Exception e = assertThrows(IllegalArgumentException.class, () -> partitaConfigService.aggiungiGiocatoreConfig("user5", "password5", mock(HttpSession.class)));
    assertEquals("Non è possibile aggiungere più di 4 giocatori.", e.getMessage());
}


    

    //TC 3.1_7
    @Test
    void AggiungiGiocatoreConfig_InserimentoStessoGiocatore() {
        String usernameOrEmail = "user1";
        String password = "password";

        Utente utente = mock(Utente.class);
        when(utente.getId()).thenReturn(1L);
        when(utente.getEmail()).thenReturn(usernameOrEmail);

        Player player = new Player();
        player.setName("Player1");
        player.setUtente(utente);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(session.getAttribute("SessionListener")).thenReturn(true);

        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);
        when(playerService.trovaGiocatore(1L)).thenReturn(player);

        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password, session);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password, session));
        assertEquals("Il giocatore è già stato aggiunto.", exception.getMessage());
    }


    //TC 3.2_1
    @Test
    void testCreazionePartita_NonCiSonoAbbastanzaGiocatori(){

        String usernameOrEmail = "user1";
        String password = "password1";
    
        Utente utente = mock(Utente.class);
        when(utente.getId()).thenReturn(1L);

         HttpSession session = mock(HttpSession.class);
    
        Player player = new Player();
        player.setName("Player1");
        player.setUtente(utente);

        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);
        when(playerService.trovaGiocatore(1L)).thenReturn(player);
  

        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);


        Exception e = assertThrows(IllegalArgumentException.class, () -> partitaConfigService.creaPartita(session) );
        assertEquals("Devono esserci almeno due giocatori.", e.getMessage());


        
    }

    //TC 3.2_2
    @Test
    void testCreazionePartita_Successful() {
        String usernameOrEmail = "user1";
        String password = "password1";

        Utente utente = mock(Utente.class);
        when(utente.getId()).thenReturn(1L);
        when(utente.getEmail()).thenReturn(usernameOrEmail);
        Player player = new Player();
        player.setUtente(utente);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(session.getAttribute("SessionListener")).thenReturn(true);

        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);
        when(playerService.trovaGiocatore(1L)).thenReturn(player);

        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password, session);

        usernameOrEmail = "user2";
        password = "password2";

        Utente utente2 = mock(Utente.class);
        when(utente2.getId()).thenReturn(2L);
        Player player2 = new Player();
        player2.setUtente(utente2);

        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente2);
        when(playerService.trovaGiocatore(2L)).thenReturn(player2);

        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password, session);

        Partita partita = partitaConfigService.creaPartita(session);

        assertNotNull(partita);
        assertEquals(2, partita.getGiocatori().size());
        assertTrue(partita.getGiocatori().contains(player));
        assertTrue(partita.getGiocatori().contains(player2));

        verify(partitaRepository).save(any(Partita.class));
}


     //TC 3.3.1 
     @Test
     void  RimuoviGiocatore_NessunGiocatoreAggiunto(){
               
         String usernameOrEmail = "user1";
         String password = "password1";
     
         Utente utente = mock(Utente.class);
         when(utente.getId()).thenReturn(1L);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(session.getAttribute("SessionListener")).thenReturn(true);
     
         Player player = new Player();
         player.setName("Player1");
         player.setUtente(utente);
 
         when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);
 
         when(playerService.trovaGiocatore(1L)).thenReturn(player);
 
         partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);
 
         Exception e = assertThrows(IllegalArgumentException.class, () ->partitaConfigService.rimuoviGiocatore());
 
         assertEquals("Non ci sono giocatori da rimuovere.", e.getMessage());
 
     }
 
 
     //TC 3.3.2
     @Test
     void RimuoviGiocatore_Successful(){
 
         
         String usernameOrEmail = "user1";
         String password = "password1";
     
         Utente utente = mock(Utente.class);
         when(utente.getId()).thenReturn(1L);
         when(utente.getEmail()).thenReturn(usernameOrEmail);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(session.getAttribute("SessionListener")).thenReturn(true);
     
         Player player = new Player();
         player.setName("Player1");
         player.setUtente(utente);
         when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);
         when(playerService.trovaGiocatore(1L)).thenReturn(player);
        
         partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);
 
         usernameOrEmail = "user2";
         password = "password2";
     
         Utente utente2 = mock(Utente.class);
         when(utente2.getId()).thenReturn(2L);
        
         player = new Player();
         player.setName("Player2");
         player.setUtente(utente2);
 
         when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente2);
         when(playerService.trovaGiocatore(2L)).thenReturn(player);
 
         partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);
 
 
         partitaConfigService.rimuoviGiocatore();
 
     
         List<String> giocatori = partitaConfigService.getGiocatoriInConfigurazione();
         assertEquals(1, giocatori.size());
 
     }
 
}
