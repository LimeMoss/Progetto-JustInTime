package com.justInTime.controller;

import com.justInTime.model.Utente;
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
    public Utente creaUtenza(@RequestBody Utente utenza) {
        return utenzaService.creaUtente(utenza);
    }

    // Ottieni una utenza per ID
    @GetMapping("/{id}")
    public Utente trovaUtenza(@PathVariable Long id) {
        return utenzaService.trovaUtente(id);
    }

    // Ottieni tutte le utenze
    @GetMapping
    public List<Utente> trovaTutteUtenze() {
        return utenzaService.trovaTutteUtenze();
    }

    // Aggiorna un'utenza esistente
    @PutMapping("/{id}")
    public Utente aggiornaUtenza(@PathVariable Long id, @RequestBody Utente utenzaAggiornata, String password2) {
        return utenzaService.aggiornaUtente(id, utenzaAggiornata, password2);
    }

    // Elimina un'utenza
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaUtenza(@PathVariable Long id) {
        utenzaService.eliminaUtente(id);
    }
}
