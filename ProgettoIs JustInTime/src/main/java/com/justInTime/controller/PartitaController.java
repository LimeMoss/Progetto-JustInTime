package com.justInTime.controller;

import com.justInTime.model.EndGameState;
import com.justInTime.model.Partita;
import com.justInTime.model.PauseState;
import com.justInTime.model.StartGameState;
import com.justInTime.service.PartitaService;
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
     * @param partitaId l'ID della partita
     * @param cartaIndex l'indice della carta da giocare
     * @param session la sessione HTTP
     * @return ResponseEntity con il risultato dell'operazione
     */
    @PostMapping("/play-card/}/{cartaIndex}")
    public ResponseEntity<String> playCard( @PathVariable int cartaIndex, HttpSession session) {
        try {
            // Prendi la partita dalla sessione usando l'ID
            Partita partita = (Partita) session.getAttribute("partita");
            partitaService.giocaCarta(partita.getId(), cartaIndex); // Chiama il servizio per giocare la carta
            return ResponseEntity.ok("Carta giocata con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    /**
     * Imposta lo stato della partita
     * @param partitaId l'ID della partita
     * @param gameState lo stato da impostare
     * @return ResponseEntity con il risultato dell'operazione
     */
    @PostMapping("/set-game-state//{gameState}")
    public ResponseEntity<String> setGameState(@PathVariable String gameState, HttpSession session) {
        try {
            // Recupera la partita dalla sessione
            Partita partita = (Partita) session.getAttribute("partita");

            switch (gameState.toLowerCase()) {
                case "start":
                    partitaService.setGameState(partita.getId(), new StartGameState());
                    break;
                case "pause":
                    partitaService.setGameState(partita.getId(), new PauseState());
                    break;
                case "end":
                    partitaService.setGameState(partita.getId(), new EndGameState());
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stato di gioco non valido.");
            }

            return ResponseEntity.ok("Stato del gioco aggiornato.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }



    /**
     * Pesca una carta dal mazzo per il giocatore corrente.
     * @param partitaId l'ID della partita
     * @param session la sessione HTTP
     * @return ResponseEntity con il risultato dell'operazione
     */
    @PostMapping("/pesca-carta/")
    public ResponseEntity<String> pescaCarta(HttpSession session) {
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            partitaService.pescaCarta(partita.getId());
            return ResponseEntity.ok("Carta pescata con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    /**
     * Termina la partita con l'ID specificato.
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

    @PostMapping("playerReady")
    public ResponseEntity<?> nextPlayerReady(HttpSession session) {
    Partita partita = (Partita) session.getAttribute("partita");

    if (partita == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Partita non trovata nella sessione.");
    }

    partitaService.playerReady(partita.getId());
    return ResponseEntity.ok().build(); 
}

    
}
