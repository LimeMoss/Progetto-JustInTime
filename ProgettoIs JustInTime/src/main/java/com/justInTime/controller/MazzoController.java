package com.justInTime.controller;


import com.justInTime.model.Carta;

import com.justInTime.Service.MazzoService;  
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mazzo")
public class MazzoController {

    private final MazzoService mazzoservice;

    public MazzoController(MazzoService mazzoservice) {
        this.mazzoservice = mazzoservice;
    }


    // Endpoint per pescare una carta dal mazzo
    @GetMapping("/pesca")
    public ResponseEntity<Carta> pescaCarta() {
        Carta carta = mazzoservice.pescaCarta();
        if (carta != null) {
            return ResponseEntity.ok(carta);
        } else {
            return ResponseEntity.status(404).body(null);  // Mazzo vuoto
        }
    }

    // Endpoint per aggiungere una carta al mazzo
    @PostMapping("/aggiungi")
    public ResponseEntity<String> aggiungiCarta(@RequestBody Carta carta) {
        mazzoservice.aggiungiCarta(carta);
        return ResponseEntity.ok("Carta aggiunta con successo.");
    }

    // Endpoint per rimuovere una carta dal mazzo
    @PostMapping("/rimuovi")
    public ResponseEntity<String> rimuoviCarta(@RequestBody Carta carta) {
        mazzoservice.rimuoviCarta(carta);
        return ResponseEntity.ok("Carta rimossa con successo.");
    }

    // Endpoint per ottenere una carta specifica dal mazzo
    @GetMapping("/get/{index}")
    public ResponseEntity<Carta> getCarta(@PathVariable int index) {
        Carta carta = mazzoservice.getCarta(index);
        if (carta != null) {
            return ResponseEntity.ok(carta);
        } else {
            return ResponseEntity.status(404).body(null);  // Carta non trovata
        }
    }



}
