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

        when(utenzaService.login(usernameOrEmail, password)).thenReturn(null);

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

    when(utenzaService.login(usernameOrEmail, password)).thenReturn(null);

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
    
    Exception e = assertThrows(IllegalArgumentException.class, () -> partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password));
    assertEquals("Credenziali non valide.", e.getMessage());

    }
    //TC 3.1_5
    @Test
    void AggiungiGiocatoreConfig_NoPassword() {

    String usernameOrEmail = "user1";
    String password = null;

    Exception e = assertThrows(IllegalArgumentException.class, () -> partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password));
    assertEquals("Credenziali non valide.", e.getMessage());

    }

    //TC 3.1.6
    @Test
    public void AggiungiGiocatoreConfig_MaxGiocatoriRaggiunto(){

        String usernameOrEmail = "user1";
        String password = "password1";
    
        Utente utente = mock(Utente.class);
        when(utente.getId()).thenReturn(1L);
        when(utente.getEmail()).thenReturn(usernameOrEmail);
    
        Player player = new Player();
        player.setName("Player1");
        player.setUtente(utente);
  
        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);

        when(playerService.trovaGiocatore(1L)).thenReturn(player);

        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);

        usernameOrEmail = "user2";
        password = "password2";
    
        utente = mock(Utente.class);
        when(utente.getId()).thenReturn(2L);
        when(utente.getEmail()).thenReturn("user2");


        player = new Player();
        player.setName("Player2");
        player.setUtente(utente);

        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);

        when(playerService.trovaGiocatore(2L)).thenReturn(player);

        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);

        usernameOrEmail = "user3";
        password = "password3";

        utente = mock(Utente.class);
        when(utente.getId()).thenReturn(3L);
        when(utente.getEmail()).thenReturn("user3");

      
        player = new Player();
        player.setName("Player3");
        player.setUtente(utente);
     
        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);

        when(playerService.trovaGiocatore(3L)).thenReturn(player);

        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);
        
        usernameOrEmail = "user4";
        password = "password4";
    
        utente = mock(Utente.class);
        when(utente.getId()).thenReturn(4L);
   

        player = new Player();
        player.setName("Player4");
        player.setUtente(utente);

        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);

        when(playerService.trovaGiocatore(4L)).thenReturn(player);


        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);

        String usernameOrEmail5 = "user5";
        String password5 = "password5";
    
        Utente utente5 = mock(Utente.class);
    
    
        Player player5 = new Player();
        player5.setName("Player5");
        player5.setUtente(utente5);


    
        Exception e = assertThrows(IllegalArgumentException.class, () -> partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail5, password5)  );
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

        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);
        when(playerService.trovaGiocatore(1L)).thenReturn(player);

        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password)    );
        assertEquals("Il giocatore è già stato aggiunto.", exception.getMessage());

        verify(utente).getEmail();
    


        assertEquals(1, partitaConfigService.getGiocatoriInConfigurazione().size());
        verify(utente).getEmail();
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
    void testCreazionePartita_Successful(){

        String usernameOrEmail = "user1";
        String password = "password1";
    
        Utente utente = mock(Utente.class);
        when(utente.getId()).thenReturn(1L);

        Player player = new Player();
        player.setName("Player1");
        player.setUtente(utente);
        when(utente.getEmail()).thenReturn(usernameOrEmail);
        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente);
        when(playerService.trovaGiocatore(1L)).thenReturn(player);

         HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("utente")).thenReturn(utente);


        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);

        usernameOrEmail = "user2";
        password = "password2";
    
        Utente utente2 = mock(Utente.class);
        when(utente2.getId()).thenReturn(2L);
  
    
        Player player2 = new Player();
        player2.setName("Player2");
        player2.setUtente(utente2);
        when(utenzaService.login(usernameOrEmail, password)).thenReturn(utente2);
        when(playerService.trovaGiocatore(2L)).thenReturn(player2);

        partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);

        Partita mockPartita = new Partita();
        mockPartita.setGiocatori(new ArrayList<>(List.of(player, player2)));

        when(partitaRepository.save(any(Partita.class))).thenReturn(mockPartita);

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
