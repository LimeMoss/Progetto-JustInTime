package com.justInTime.controller;

import com.justInTime.model.Utente;
import com.justInTime.service.UtenzaService;

import jakarta.servlet.http.HttpSession;

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


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Utente creaUtenza(@RequestBody Utente utenza) {
        return utenzaService.creaUtente(utenza);
    }


    @GetMapping("/trovaUtenza")
    public Utente trovaUtenza(HttpSession session) {
        Utente utente = (Utente)session.getAttribute("utente");
        return utenzaService.trovaUtente(utente.getId());
    }
    @GetMapping
    public List<Utente> trovaTutteUtenze() {
        return utenzaService.trovaTutteUtenze();
    }


    @PutMapping("/modificautenza")
    public Utente aggiornaUtenza(@RequestBody Utente utenzaAggiornata, String password2, HttpSession session) {
        Utente utente = (Utente)session.getAttribute("utente");
        return utenzaService.aggiornaUtente(utente.getId(), utenzaAggiornata, password2);
    }

    @DeleteMapping("/rimuoviUtenza")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaUtenza(HttpSession session) {
        Utente utente = (Utente)session.getAttribute("utente");
        utenzaService.eliminaUtente(utente.getId());
    }
}
