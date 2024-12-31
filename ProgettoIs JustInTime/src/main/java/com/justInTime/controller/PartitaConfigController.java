package com.justInTime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.justInTime.model.Partita;

import com.justInTime.service.PartitaConfigService;
import com.justInTime.service.PartitaService;

@RestController
@RequestMapping("/api/game-config")
public class PartitaConfigController {
    
    @Autowired
    private PartitaConfigService partitaConfigService;
    
    @Autowired
    private PartitaService partitaService;

    @PostMapping("/add-players")
    public ResponseEntity<Void> addPlayer(@RequestParam String usernameOrEmail, @RequestParam String password) {
        try {
            partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }




    @DeleteMapping("/remove-player")
    public ResponseEntity<String> removePlayer() {
        try {
            partitaConfigService.rimuoviGiocatore();
            return ResponseEntity.ok("Giocatore rimosso con successo.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }


    @GetMapping("/players")
    public ResponseEntity<List<String>> getConfiguredPlayers() {
        List<String> playerNames = partitaConfigService.getGiocatoriInConfigurazione();
        return ResponseEntity.ok(playerNames);
    }

    // Crea e avvia la partita
    @PostMapping("/create-and-start")
    public ResponseEntity<Partita> createAndStartGame() {
        try {
            Partita newPartita = partitaConfigService.creaPartita();
            partitaService.iniziaPartita(newPartita);  // Metodo per iniziare la partita
            return ResponseEntity.ok(newPartita);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }




    @GetMapping("/is-player-in-game")
    public ResponseEntity<Boolean> isPlayerInGame(@RequestParam Long playerId, @RequestParam Long partitaId) {
        try {
            boolean isInGame = partitaConfigService.isGiocatoreInPartita(playerId, partitaId);
            return ResponseEntity.ok(isInGame);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
}
