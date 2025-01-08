package com.justInTime.controller;

import com.justInTime.model.Carta;
import com.justInTime.model.Partita;

import com.justInTime.service.PartitaService;

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
            // Prendi la partita dalla sessione usando l'ID
            Partita partita = (Partita) session.getAttribute("partita");
            Carta carta = partitaService.giocaCarta(partita.getId(), cartaIndex); 
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
            if (partita == null) {
            
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("errore", "Partita non trovata"));
            }
    
    
            Carta carta = partitaService.pescaCarta(partita.getId());
     
    
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
    public ResponseEntity<String> terminaPartita(HttpSession session) {
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            partitaService.terminaPartita(partita.getId());
            return ResponseEntity.ok("Partita terminata con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    @PostMapping("/playerMano")
    public ResponseEntity<Object> getGiocatoreCorrenteMano(HttpSession session) {
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            return ResponseEntity.ok(partitaService.getGiocatoreCorrenteMano(partita.getId()));
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

        if (partita == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Partita non trovata nella sessione.");
        }

        partitaService.nextplayerReady(partita.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/PlayerReady")
    public ResponseEntity<?> PlayerReady(HttpSession session) {
        Partita partita = (Partita) session.getAttribute("partita");

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
            return ResponseEntity.ok(partitaService.getCurrentPlayerTimer(partita.getId()));
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
    public ResponseEntity<?> getTNameINdexPlayer(HttpSession session) {

        try {
            Partita partita = (Partita) session.getAttribute("partita");
            return ResponseEntity
                    .ok(partitaService.getPartita(partita.getId()).getGiocatoreCorrente().getUtente().getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giocatore non trovato nella partita.");
        }

    }


    @GetMapping("/last-discarded-card/")
    public ResponseEntity<?> lastDiscardedCard(HttpSession session) {
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            Carta carta = partitaService.getLastCardScarto(partita.getId());
            return ResponseEntity.ok(carta); 
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("errore", e.getMessage()));
        }
    }

}
