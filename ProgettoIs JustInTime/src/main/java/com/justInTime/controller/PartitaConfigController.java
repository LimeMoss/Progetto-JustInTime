package com.justInTime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.service.PartitaConfigService;
import com.justInTime.service.PartitaService;

@RestController
@RequestMapping("/api/partite")
public class PartitaController {

    
    @Autowired
    private PartitaService partitaService;
    private PartitaConfigService partitaConfigService;

        @PostMapping
    public ResponseEntity<Partita> creaPartita() {
        Partita partita = partitaConfigService.createPartita();
        return ResponseEntity.ok(partita);
    }

    @PostMapping("/{partitaId}/giocatori")
    public ResponseEntity<Partita> aggiungiGiocatore(
            @PathVariable Long partitaId,
            @RequestBody Player giocatore) {
        return ResponseEntity.ok(partitaConfigService.aggiungiGiocatore(partitaId, giocatore));
    }

    @DeleteMapping("/{partitaId}/giocatori/{giocatoreId}")
    public ResponseEntity<Void> rimuoviGiocatore(
            @PathVariable Long partitaId,
            @PathVariable Long giocatoreId) {
        partitaConfigService.rimuoviGiocatore(partitaId, giocatoreId);
        return ResponseEntity.ok().build();
    }



}