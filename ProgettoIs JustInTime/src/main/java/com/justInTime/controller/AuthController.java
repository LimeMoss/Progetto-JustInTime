package com.justInTime.controller;

import com.justInTime.model.Utente;
import com.justInTime.service.UtenzaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class AuthController {

    private final UtenzaService utenzaService;

    public AuthController(UtenzaService utenzaService) {
        this.utenzaService = utenzaService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registraUtente(@RequestBody Utente utente, @RequestParam String password2) {
        try {
   
            utenzaService.registerUser(utente, password2);
     
            RedirectView redirectView = new RedirectView("/login");
            return new ResponseEntity<>(redirectView, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            RedirectView redirectView = new RedirectView("/login?error=" + "Registrazione non andata a buon fine");
            return new ResponseEntity<>(redirectView, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<RedirectView> login(@RequestParam String usernameOrEmail, @RequestParam String password) {
        try {
            utenzaService.login(usernameOrEmail, password);
            RedirectView redirectView = new RedirectView("/homepage");
            return new ResponseEntity<>(redirectView, HttpStatus.OK);
        } catch (RuntimeException e) {
            RedirectView redirectView = new RedirectView("/login?error=" + "Username o password errati");
            return new ResponseEntity<>(redirectView, HttpStatus.UNAUTHORIZED);
        }
    }
}
