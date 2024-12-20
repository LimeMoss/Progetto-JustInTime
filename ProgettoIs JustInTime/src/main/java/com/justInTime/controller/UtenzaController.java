package com.justInTime.controller;

import com.justInTime.model.Utenza;
import com.justInTime.service.UtenzaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utenze")
public class UtenzaController {

    private final UtenzaService utenzaService;

    public UtenzaController(UtenzaService utenzaService) {
        this.utenzaService = utenzaService;
    }

    // Crea una nuova utenza
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Utenza creaUtenza(@RequestBody Utenza utenza) {
        return utenzaService.creaUtenza(utenza);
    }

    // Ottieni una utenza per ID
    @GetMapping("/{id}")
    public Utenza trovaUtenza(@PathVariable Long id) {
        return utenzaService.trovaUtenza(id);
    }

    // Ottieni tutte le utenze
    @GetMapping
    public List<Utenza> trovaTutteUtenze() {
        return utenzaService.trovaTutteUtenze();
    }

    // Aggiorna un'utenza esistente
    @PutMapping("/{id}")
    public Utenza aggiornaUtenza(@PathVariable Long id, @RequestBody Utenza utenzaAggiornata) {
        return utenzaService.aggiornaUtenza(id, utenzaAggiornata);
    }

    // Elimina un'utenza
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaUtenza(@PathVariable Long id) {
        utenzaService.eliminaUtenza(id);
    }
}
