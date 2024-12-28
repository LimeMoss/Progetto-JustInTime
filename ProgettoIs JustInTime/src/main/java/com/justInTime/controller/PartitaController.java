package com.justInTime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.justInTime.model.Partita;

import com.justInTime.service.PartitaService;


@RestController
@RequestMapping("/api/game")
public class PartitaController {
    
    @Autowired
    private PartitaService partitaService;
    
    @PostMapping("/{partitaId}/play/{cardIndex}")
    public ResponseEntity<Partita> playCard(
            @PathVariable Long partitaId,
            @PathVariable int cardIndex) {
        try {
            Partita updatedPartita = partitaService.giocaCarta(partitaId, cardIndex);
            return ResponseEntity.ok(updatedPartita);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{partitaId}")
    public ResponseEntity<Partita> getGame(@PathVariable Long partitaId) {
        try {
            Partita partita = partitaService.getPartita(partitaId);
            return ResponseEntity.ok(partita);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{partitaId}/end")
    public ResponseEntity<Void> endGame(@PathVariable Long partitaId) {
        try {
            partitaService.terminaPartita(partitaId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}