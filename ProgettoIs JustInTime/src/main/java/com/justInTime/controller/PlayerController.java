package com.justInTime.controller;

import com.justInTime.model.Carta;
import com.justInTime.model.Player;
import com.justInTime.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giocatore")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Crea un nuovo giocatore
    @PostMapping("/crea")
    public ResponseEntity<Player> creaGiocatore(@RequestParam String name, @RequestParam int maxScore) {
        Player player = playerService.creaGiocatore(name, maxScore);
        return ResponseEntity.ok(player);
    }

    // Trova un giocatore per ID
    @GetMapping("/{playerId}")
    public ResponseEntity<Player> trovaGiocatore(@PathVariable Long playerId) {
        Player player = playerService.trovaGiocatore(playerId);
        return ResponseEntity.ok(player);
    }

    // Ottieni tutti i giocatori
    @GetMapping("/tutti")
    public ResponseEntity<List<Player>> trovaTuttiGiocatori() {
        List<Player> players = playerService.trovaTuttiGiocatori();
        return ResponseEntity.ok(players);
    }

    // Aggiorna il nome del giocatore
    @PutMapping("/{playerId}/aggiorna-nome")
    public ResponseEntity<Player> aggiornaNomeGiocatore(@PathVariable Long playerId, @RequestParam String nuovoNome) {
        Player player = playerService.aggiornaNomeGiocatore(playerId, nuovoNome);
        return ResponseEntity.ok(player);
    }

    // Aggiungi una carta alla mano del giocatore
    @PostMapping("/{playerId}/aggiungi-carta")
    public ResponseEntity<Player> aggiungiCartaAllaMano(@PathVariable Long playerId, @RequestBody Carta carta) {
        Player player = playerService.aggiungiCartaAllaMano(playerId, carta);
        return ResponseEntity.ok(player);
    }

    // Rimuovi una carta dalla mano del giocatore
    @PostMapping("/{playerId}/rimuovi-carta")
    public ResponseEntity<Player> rimuoviCartaDallaMano(@PathVariable Long playerId, @RequestBody Carta carta) {
        Player player = playerService.rimuoviCartaDallaMano(playerId, carta);
        return ResponseEntity.ok(player);
    }

    // Incrementa il punteggio massimo del giocatore
    @PostMapping("/{playerId}/incrementa-punteggio")
    public ResponseEntity<Player> incrementaMaxScore(@PathVariable Long playerId) {
        Player player = playerService.incrementaMaxScore(playerId);
        return ResponseEntity.ok(player);
    }
}