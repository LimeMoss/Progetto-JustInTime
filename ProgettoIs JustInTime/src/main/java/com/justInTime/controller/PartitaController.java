package com.justInTime.controller;

import com.justInTime.model.Carta;
import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.service.PartitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partite")
public class PartitaController {

    @Autowired
    private PartitaService partitaService;

    @PostMapping
    public ResponseEntity<Partita> createPartita() {
        Partita partita = partitaService.createPartita();
        return ResponseEntity.ok(partita);
    }

    @PostMapping("/{partitaId}/giocatori")
    public ResponseEntity<Partita> aggiungiGiocatore(
            @PathVariable Long partitaId,
            @RequestBody Player giocatore) {
        return ResponseEntity.ok(partitaService.aggiungiGiocatore(partitaId, giocatore));
    }

    @DeleteMapping("/{partitaId}/giocatori/{giocatoreId}")
    public ResponseEntity<Void> rimuoviGiocatore(
            @PathVariable Long partitaId,
            @PathVariable Long giocatoreId) {
        partitaService.rimuoviGiocatore(partitaId, giocatoreId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{partitaId}/start")
    public ResponseEntity<Partita> iniziaPartita(@PathVariable Long partitaId) {
        return ResponseEntity.ok(partitaService.iniziaPartita(partitaId));
    }

    @PostMapping("/{partitaId}/gioca-carta")
    public ResponseEntity<Partita> giocaCarta(
            @PathVariable Long partitaId,
            @RequestParam int cartaIndex) {
        return ResponseEntity.ok(partitaService.giocaCarta(partitaId, cartaIndex));
    }

    @GetMapping("/{partitaId}/stato")
    public ResponseEntity<Partita> getStatoPartita(@PathVariable Long partitaId) {
        return ResponseEntity.ok(partitaService.getPartita(partitaId));
    }

    @PostMapping("/{partitaId}/termina")
    public ResponseEntity<Void> terminaPartita(@PathVariable Long partitaId) {
        partitaService.terminaPartita(partitaId);
        return ResponseEntity.ok().build();
    }
}