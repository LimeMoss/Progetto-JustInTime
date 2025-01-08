package com.justInTime.controller;

import com.justInTime.model.Partita;
import com.justInTime.service.PartitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/game")
public class PartitaController {

    @Autowired
    private PartitaService partitaService;

    private static final Logger logger = LoggerFactory.getLogger(PartitaController.class);

    @PostMapping("/play-card/{cartaIndex}")
    public ResponseEntity<String> playCard(@PathVariable int cartaIndex, HttpSession session) {
        logger.info("[playCard] CartaIndex: {}, SessionID: {}", cartaIndex, session.getId());
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            logger.debug("[playCard] Partita trovata: {}", partita);
            partitaService.giocaCarta(partita.getId(), cartaIndex);
            logger.info("[playCard] Carta giocata con successo.");
            return ResponseEntity.ok("Carta giocata con successo.");
        } catch (RuntimeException e) {
            logger.error("[playCard] Errore: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    @PostMapping("/pesca-carta/")
    public ResponseEntity<String> pescaCarta(HttpSession session) {
        logger.info("[pescaCarta] SessionID: {}", session.getId());
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            logger.debug("[pescaCarta] Partita trovata: {}", partita);
            partitaService.pescaCarta(partita.getId());
            logger.info("[pescaCarta] Carta pescata con successo.");
            return ResponseEntity.ok("Carta pescata con successo.");
        } catch (RuntimeException e) {
            logger.error("[pescaCarta] Errore: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    @PostMapping("/termina-partita/")
    public ResponseEntity<String> terminaPartita(HttpSession session) {
        logger.info("[terminaPartita] SessionID: {}", session.getId());
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            logger.debug("[terminaPartita] Partita trovata: {}", partita);
            partitaService.terminaPartita(partita.getId());
            logger.info("[terminaPartita] Partita terminata con successo.");
            return ResponseEntity.ok("Partita terminata con successo.");
        } catch (RuntimeException e) {
            logger.error("[terminaPartita] Errore: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    @PostMapping("/playerMano")
    public ResponseEntity<Object> getGiocatoreCorrenteMano(HttpSession session) {
        logger.info("[getGiocatoreCorrenteMano] SessionID: {}", session.getId());
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            logger.debug("[getGiocatoreCorrenteMano] Partita trovata: {}", partita);
            return ResponseEntity.ok(partitaService.getGiocatoreCorrenteMano(partita.getId()));
        } catch (RuntimeException e) {
            logger.error("[getGiocatoreCorrenteMano] Errore: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    @PostMapping("/nextPlayerReady")
    public ResponseEntity<?> nextPlayerReady(HttpSession session) {
        logger.info("[nextPlayerReady] SessionID: {}", session.getId());
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            if (partita == null) {
                logger.warn("[nextPlayerReady] Partita non trovata nella sessione.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Partita non trovata nella sessione.");
            }
            partitaService.nextplayerReady(partita.getId());
            logger.info("[nextPlayerReady] Giocatore pronto.");
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            logger.error("[nextPlayerReady] Errore: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    @PostMapping("/PlayerReady")
    public ResponseEntity<?> PlayerReady(HttpSession session) {
        logger.info("[PlayerReady] SessionID: {}", session.getId());
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            if (partita == null) {
                logger.warn("[PlayerReady] Partita non trovata nella sessione.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Partita non trovata nella sessione.");
            }
            partitaService.playerReady(partita.getId());
            logger.info("[PlayerReady] Giocatore pronto.");
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            logger.error("[PlayerReady] Errore: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    @GetMapping("/timer")
    public ResponseEntity<?> getTimerPlayer(HttpSession session) {
        logger.info("[getTimerPlayer] SessionID: {}", session.getId());
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            logger.debug("[getTimerPlayer] Partita trovata: {}", partita);
            return ResponseEntity.ok(partitaService.getCurrentPlayerTimer(partita.getId()));
        } catch (RuntimeException e) {
            logger.error("[getTimerPlayer] Errore: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giocatore non trovato nella partita.");
        }
    }

    /**
     * Ritorna il nome dell'utente del giocatore corrente.
     * 
     * @param session la sessione HTTP
     * @return un oggetto ResponseEntity con il nome dell'utente del giocatore
     *         corrente
     */
    @GetMapping("/nameIndexPlayer")
    public ResponseEntity<?> getTNameINdexPlayer(HttpSession session) {
        logger.info("[getTNameINdexPlayer] SessionID: {}", session.getId());
        try {
            Partita partita = (Partita) session.getAttribute("partita");
            logger.debug("[getTNameINdexPlayer] Partita trovata: {}", partita);
            return ResponseEntity.ok(partitaService.getPartita(partita.getId()).getGiocatoreCorrente().getUtente().getUsername());
        } catch (RuntimeException e) {
            logger.error("[getTNameINdexPlayer] Errore: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giocatore non trovato nella partita.");
        }
    }
}
