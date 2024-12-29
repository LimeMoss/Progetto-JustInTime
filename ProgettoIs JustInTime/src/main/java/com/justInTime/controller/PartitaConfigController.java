package com.justInTime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(partitaConfigService.getGiocatoriInConfigurazione());
    }
    
    @PostMapping("/create-and-start")
    public ResponseEntity<Partita> createAndStartGame() {
        try {
            Partita newPartita = partitaConfigService.creaPartita();
        
            partitaService.iniziaPartita(newPartita.getId());
            
            return ResponseEntity.ok(newPartita);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}