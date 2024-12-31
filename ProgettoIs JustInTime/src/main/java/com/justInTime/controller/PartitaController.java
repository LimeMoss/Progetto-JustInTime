package com.justInTime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.justInTime.model.EndGameState;
import com.justInTime.model.Partita;
import com.justInTime.model.PauseState;
import com.justInTime.model.StartGameState;
import com.justInTime.service.PartitaService;


@RestController
@RequestMapping("/game")
public class PartitaController {
    
    @Autowired
    private PartitaService partitaService;
    @PostMapping("/play-card/{partitaId}/{cartaIndex}")
    public ResponseEntity<String> playCard(@PathVariable Long partitaId, @PathVariable int cartaIndex) {
        try {
             partitaService.giocaCarta(partitaId, cartaIndex);
            return ResponseEntity.ok("Carta giocata con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    

  
    @PostMapping("/set-game-state/{partitaId}/{gameState}")
    public ResponseEntity<String> setGameState(@PathVariable Long partitaId, @PathVariable String gameState) {
        try {
            Partita partita = partitaService.getPartita(partitaId);
            switch (gameState.toLowerCase()) {
                case "start":
                    partitaService.setGameState(partita, new StartGameState());
                    break;
                case "pause":
                    partitaService.setGameState(partita, new PauseState());
                    break;
                case "end":
                    partitaService.setGameState(partita, new EndGameState());
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stato di gioco non valido.");
            }
            return ResponseEntity.ok("Stato del gioco aggiornato.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

}