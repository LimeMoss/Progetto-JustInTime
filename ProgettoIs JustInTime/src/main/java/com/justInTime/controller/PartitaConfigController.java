package com.justInTime.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.justInTime.model.LoginResponse;
import com.justInTime.model.Partita;
import com.justInTime.model.Utente;
import com.justInTime.service.PartitaConfigService;
import com.justInTime.service.PartitaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/game-config")
public class PartitaConfigController {

    @Autowired
    private PartitaConfigService partitaConfigService;

    @Autowired
    private PartitaService partitaService;



    @PostMapping("/add-player-login")
    public ResponseEntity<LoginResponse> addPlayerLogin(@RequestParam String usernameOrEmail, @RequestParam String password, HttpSession session) {
        try {
       
            partitaConfigService.aggiungiGiocatoreConfig(usernameOrEmail, password, session);
    
            LoginResponse response = new LoginResponse("Login e aggiunta giocatore effettuati con successo", usernameOrEmail);
    
            return ResponseEntity.ok(response);
        } catch (Exception e) {
    
            LoginResponse errorResponse = new LoginResponse("Errore: " + e.getMessage(), "");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
    @GetMapping("/getSessionUser")
    public ResponseEntity<String> getSessionUser(HttpSession session) {
    
        Utente sessionUser = (Utente) session.getAttribute("utente");

      
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non sei autenticato. Accedi per continuare.");
        }

      
        String nome = sessionUser.getUsername(); 
        return ResponseEntity.ok(nome);
    }

 

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @DeleteMapping("/remove-player")
    public ResponseEntity<String> removePlayer() {
        try {
           String username = partitaConfigService.rimuoviGiocatore();
            return ResponseEntity.ok(username);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    @GetMapping("/players")
    public ResponseEntity<List<String>> getConfiguredPlayers(HttpSession session) {
        List<String> playerNames = partitaConfigService.getGiocatoriInConfigurazione(session);
        return ResponseEntity.ok(playerNames);
    }



    @PostMapping("/create-and-start")
    public ResponseEntity<Object> createAndStartGame(HttpSession session) {
        try {

            if (session == null || session.getAttribute("utente") == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Utente non autenticato.");
            }
    
         
            Partita newPartita = partitaConfigService.creaPartita(session);
            session.setAttribute("partita", newPartita);
    
  
            partitaService.iniziaPartita(newPartita.getId());
    
       
            ModelAndView modelAndView = new ModelAndView("match");
            modelAndView.addObject("partita", newPartita);
    
            return ResponseEntity.ok(modelAndView); 
        } catch (RuntimeException e) {
          
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Si è verificato un errore durante il processo.");
        }
    }
    
    

    @PostMapping("/play-again")
public Object playAgain(HttpSession session) {
    try {
        Partita partitaPrecedente = (Partita) session.getAttribute("partita");
        if (partitaPrecedente == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Partita nuovaPartita = partitaConfigService.creaNuovaPartitaDaPartitaPrecedente(partitaPrecedente.getId());

       
        session.setAttribute("partita", nuovaPartita);

        partitaService.iniziaPartita(nuovaPartita.getId());

        
        ModelAndView modelAndView = new ModelAndView("match"); 
        modelAndView.addObject("partita", nuovaPartita);  
    
            return modelAndView;  
    } catch (RuntimeException e) {
              return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Si è verificato un errore durante il processo.");
    }
}

    


}
