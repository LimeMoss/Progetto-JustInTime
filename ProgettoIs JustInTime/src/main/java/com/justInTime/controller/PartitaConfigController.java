package com.justInTime.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.justInTime.DTO.LoginResponse;
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
    public ResponseEntity<String> removePlayer(HttpSession session) {
        try {
           String username = partitaConfigService.rimuoviGiocatore(session);
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




    
    /**
     * Crea una partita con i giocatori attualmente in configurazione e li
     * associa alla partita.
     * La partita viene salvata e lo stato della partita viene impostato su
     * "inizio partita".
     * La configurazione dei giocatori viene ripulita.
     * Il metodo restituisce un oggetto ResponseEntity con un oggetto ModelAndView
     * come body. Il ModelAndView contiene la vista "match" e l'oggetto partita
     * appena creata.
     *
     * @param session la sessione HTTP
     * @return un oggetto ResponseEntity con il ModelAndView come body
     * @throws RuntimeException se si verifica un errore durante il processo
     */
 @PostMapping("/create-and-start")
public ResponseEntity<Object> createAndStartGame(HttpSession session) {
    try {
      
    


       
        Partita newPartita = partitaConfigService.creaPartita(session);


     
        session.setAttribute("partita", newPartita);
   
        partitaService.iniziaPartitaAsync(newPartita);
    
        return ResponseEntity.ok().body("success");

    } catch (Exception e) {
     

   
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Si è verificato un errore durante il processo.");
    }
}


    @GetMapping("/match-status")
    public ResponseEntity<Boolean> getPartitaStatus(HttpSession session) {

        Partita partita = (Partita) session.getAttribute("partita");
        boolean status = partitaService.isFinished(partita);
        
        return ResponseEntity.ok(status);
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

        partitaService.iniziaPartita(nuovaPartita);

        
        ModelAndView modelAndView = new ModelAndView("match"); 
        modelAndView.addObject("partita", nuovaPartita);  
    
            return modelAndView;  
    } catch (RuntimeException e) {
              return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Si è verificato un errore durante il processo.");
    }
}

    


}
