package com.justInTime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justInTime.model.Partita;

import com.justInTime.service.PartitaConfigService;
import com.justInTime.service.PartitaService;

@RestController
@RequestMapping("/api/partite")
public class PartitaController {

    @Autowired
    private PartitaService partitaService;
    private PartitaConfigService partitaConfigService;


    
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