package com.justInTime.controller;

import com.justInTime.DTO.FullPlayerDataDTO;
import com.justInTime.model.Carta;
import com.justInTime.model.Partita;
import com.justInTime.model.PauseState;
import com.justInTime.model.Player;
import com.justInTime.service.PartitaConfigService;
import com.justInTime.service.PartitaService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/game")
public class PartitaController {

    @Autowired
    private PartitaService partitaService;
    @Autowired
    private PartitaConfigService partitaConfigService;

    /**
     * Gioca una carta dalla mano del giocatore corrente e passa il turno.
     * 
     * @param partitaId  l'ID della partita
     * @param cartaIndex l'indice della carta da giocare
     * @param session    la sessione HTTP
     * @return ResponseEntity con il risultato dell'operazione
     */
    @PostMapping("/play-card/{cartaIndex}")
    public ResponseEntity<?> playCard(@PathVariable int cartaIndex, HttpSession session) {
        try {
         
      
            Partita partita = (Partita) session.getAttribute("partita");
            partita = partitaService.getPartita(partita.getId());    
            Carta carta = partitaService.giocaCarta(partita, cartaIndex); 
            System.out.println("Carta giocata: " + carta.getValore());
            return ResponseEntity.ok(carta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("errore", e.getMessage()));
        }
    }

    /**
     * Pesca una carta dal mazzo per il giocatore corrente.
     * 
     * @param partitaId l'ID della partita
     * @param session   la sessione HTTP
     * @return ResponseEntity con il risultato dell'operazione
     */
     @PostMapping("/pesca-carta/")
     public ResponseEntity<?> pescaCarta(HttpSession session) {

         try {
    
           
            Partita partita = (Partita) session.getAttribute("partita");
            partita = partitaService.getPartita(partita.getId());    
    
             if (partita == null) {

                 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .body(Map.of("errore", "Partita non trovata"));
             }
     
           
             Carta carta = partitaService.pescaCarta(partita);
       
     
             return ResponseEntity.ok(carta); 
         } catch (RuntimeException e) {
    
             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                     .body(Map.of("errore", e.getMessage()));
         }
     }
     
    /**
     * Termina la partita con l'ID specificato.
     * 
     * @param partitaId l'ID della partita
     * @return ResponseEntity con il risultato dell'operazione
     */
    @PostMapping("/termina-partita/")
    public ResponseEntity<?> terminaPartita(HttpSession session) {
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            partita = partitaService.getPartita(partita.getId());    
            partitaService.terminaPartita(partita);
    
            return ResponseEntity.ok(partitaService.terminaPartita(partita));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    @PostMapping("/playerMano")
    public ResponseEntity<Object> getGiocatoreCorrenteMano(HttpSession session) {
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            partita = partitaService.getPartita(partita.getId());    
            return ResponseEntity.ok(partitaService.getGiocatoreCorrenteMano(partita));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }

    }

    /**
     * Segnala che il giocatore corrente è pronto per continuare la partita.
     * Il metodo imposta il flag nextPlayerReady a true, permettendo il
     * proseguimento
     * del gioco dallo stato di pausa.
     * 
     * @param session la sessione HTTP
     * @return ResponseEntity con il risultato dell'operazione
     */
  
    @PostMapping("/nextPlayerReady")
    public ResponseEntity<?> nextPlayerReady(HttpSession session) {
        Partita partita = (Partita) session.getAttribute("partita");
        partita = partitaService.getPartita(partita.getId());    

      

   
        if (partita.getGameState() instanceof PauseState) {
            PauseState pauseState = (PauseState) partita.getGameState();  
            pauseState.notifyPlayerReady(partita.getId());  
        }
   
        return ResponseEntity.ok().build();
    }

    @PostMapping("/PlayerReady")
    public ResponseEntity<?> PlayerReady(HttpSession session) {
        Partita partita = (Partita) session.getAttribute("partita");
        partita = partitaService.getPartita(partita.getId());    

        if (partita == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Partita non trovata nella sessione.");
        }

        partitaService.playerReady(partita.getId());
        return ResponseEntity.ok().build();
    }

    /**
     * Ritorna il tempo rimanente del giocatore corrente della partita con l'ID
     * specificato.
     * 
     * @param session la sessione HTTP
     * @return ResponseEntity con il tempo rimanente del giocatore corrente
     * @throws RuntimeException se non esiste una partita con l'ID specificato o
     *                          se il giocatore non esiste nella partita
     */

    @GetMapping("/timer")
    public ResponseEntity<?> getTimerPlayer(HttpSession session) {

        try {
            Partita partita = (Partita) session.getAttribute("partita");
            partita = partitaService.getPartita(partita.getId());    
            return ResponseEntity.ok(partitaService.getCurrentPlayerTimer(partita));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giocatore non trovato nella partita.");
        }

    }

    /**
     * Ritorna il nome dell'utente del giocatore corrente della partita con l'ID
     * specificato.
     * 
     * @param session la sessione HTTP
     * @return ResponseEntity con il nome del giocatore corrente
     * @throws RuntimeException se il giocatore non viene trovato nella partita
     */
    @GetMapping("/nameIndexPlayer")
    public ResponseEntity<?> getNameINdexPlayer(HttpSession session) {
    
        
        try {
    
            Partita partita = (Partita) session.getAttribute("partita");
            partita = partitaService.getPartita(partita.getId());    
    
            if (partita == null) {
             
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Partita non trovata nella sessione.");
            }

            
            String username = partita.getGiocatoreCorrenteless().getUtente().getUsername();
            System.out.println("Username del giocatore corrente nel controller: " + username);
            

            return ResponseEntity.ok(username);
    
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giocatore non trovato nella partita.");
        }
    }

    @GetMapping("/last-discarded-card/")
    public ResponseEntity<?> lastDiscardedCard(HttpSession session) {
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            partita = partitaService.getPartita(partita.getId());    
            if (partita == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("errore", "Partita non trovata nella sessione"));
            }
    
            Carta carta = partitaService.getLastCardScarto(partita);
            System.out.println("Carta scartata: " + carta.getValore());
            
            if (carta == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Map.of("messaggio", "Nessuna carta scartata", "carta", null));
            }
    
            return ResponseEntity.ok(carta);
            
        } catch (Exception e) {
       
            String errorMessage = e.getMessage() != null ? e.getMessage() : "Errore sconosciuto";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("errore", "Errore interno del server", "dettagli", errorMessage));
        }
    }

    

    @PostMapping("/nextPlayer")
    public String goNextPlayer(HttpSession session) {
        Partita partita = (Partita) session.getAttribute("partita");
        partita = partitaService.getPartita(partita.getId()); 
        if (partita == null) {
            return "Partita non trovata nella sessione!";
        }
        
        partita = partitaService.getPartita(partita.getId());
        if (partita == null) {
            return "Partita non esistente!";
        }
    
        if (partita.getGameState() == null) {
            return "Stato di gioco non valido!";
        }
    
        try {
            if (partita.getGameState() instanceof PauseState) {
                Player player = partita.getGiocatoreCorrente();
    
                if (player == null) {
                    return "Non ci sono giocatori successivi disponibili!";
                }
    
                System.out.println("L'utente successivo è " + player.getUtente().getUsername());
                return player.getUtente().getUsername();
            } else {
                return "Stato di gioco è: " + partita.getGameState().getClass().getSimpleName();
            }
        } catch (Exception e) {
            return "Errore nella presa del giocatore successivo: " + e.getMessage();
        }
    }



    @PostMapping("/nextPlayer2")
    public List<String> goNextPlayer2(HttpSession session) {
        List<String> giocatori=partitaConfigService.getGiocatoriInConfigurazione(session);
        System.out.println(giocatori);
        return partitaConfigService.getGiocatoriInConfigurazione(session);
    }
    


}
