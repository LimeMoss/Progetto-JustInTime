package com.justInTime.controller;

import com.justInTime.model.Utente;
import com.justInTime.service.UtenzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final UtenzaService utenzaService;

    public AuthController(UtenzaService utenzaService) {
        this.utenzaService = utenzaService;
    }

    // Endpoint per la registrazione
    @PostMapping("/register")
    public ResponseEntity<Utente> registerUser(@RequestBody Utente utente) {
        try {
            Utente nuovoUtente = utenzaService.registerUser(utente);
            return new ResponseEntity<>(nuovoUtente, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint per il login
    @PostMapping("/login")
    public ResponseEntity<Utente> login(@RequestParam String usernameOrEmail, @RequestParam String password) {
        try {
            Utente utente = utenzaService.login(usernameOrEmail, password);
            return new ResponseEntity<>(utente, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
