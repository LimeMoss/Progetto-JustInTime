package com.justInTime.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.justInTime.model.Player;
import com.justInTime.service.PlayerService;


@RestController
@RequestMapping("/giocatore")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }



    /**
     * Endpoint per trovare un giocatore per ID.
     *
     * @param playerId l'ID del giocatore da trovare
     * @return ResponseEntity contenente l'oggetto Player se trovato
     */
    @GetMapping("/{playerId}")
    public ResponseEntity<Player> trovaGiocatore(@PathVariable Long playerId) {
        Player player = playerService.trovaGiocatore(playerId);
        return ResponseEntity.ok(player);
    }



    /**
     * Endpoint per ottenere la lista di tutti i giocatori.
     * 
     * @return lista di Player
     */
    @GetMapping("/tutti")
    public ResponseEntity<List<Player>> trovaTuttiGiocatori() {
        List<Player> players = playerService.trovaTuttiGiocatori();
        return ResponseEntity.ok(players);
    }




}