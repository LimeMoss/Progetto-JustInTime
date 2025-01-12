package com.justInTime.controller;

import com.justInTime.DTO.FullPlayerDataDTO;
import com.justInTime.DTO.FullPlayerDataDTOPsw;
import com.justInTime.DTO.paeseUtenzaDTO;
import com.justInTime.model.Utente;
import com.justInTime.service.PlayerService;
import com.justInTime.service.UtenzaService;

import jakarta.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/utenze")
public class UtenzaController {

    @Autowired
    private UtenzaService utenzaService;
    @Autowired
    private PlayerService playerService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Utente creaUtenza(@RequestBody Utente utenza) {
        return utenzaService.creaUtente(utenza);
    }



    @GetMapping("/trovaUtenza")
    public FullPlayerDataDTO trovaUtenza(HttpSession session) {
        Utente utente = (Utente)session.getAttribute("utente");
        return utenzaService.trovaUtenteNoPsw(utente.getId());
    }

    @GetMapping("/trovaUtenzaPsw")
    public FullPlayerDataDTOPsw trovaUtenzaPsw(HttpSession session) {
        Utente utente = (Utente)session.getAttribute("utente");
        return utenzaService.trovaUtenteConPsw(utente.getId());
    }

    


    @GetMapping("/trovaUtenzaPaese")
    public paeseUtenzaDTO trovaUtenzaPaese(HttpSession session) {
        Utente utente = (Utente)session.getAttribute("utente");
        return utenzaService.trovaUtentePaese(utente.getId());
    }
    @GetMapping("/trovaTutteUtenze")
    public List<Utente> trovaTutteUtenze() {
        return utenzaService.trovaTutteUtenze();
    }


    @PutMapping("/modificautenza")
    public Object aggiornaUtenza(@RequestBody Utente utenzaAggiornata, String password2, HttpSession session) {
       
        try{
        Utente utente = (Utente)session.getAttribute("utente");
        utente = utenzaService.aggiornaUtente(utente.getId(), utenzaAggiornata, password2);
        ModelAndView modelAndView = new ModelAndView("homepage"); 
        modelAndView.addObject("utente", utente);
        session.setAttribute("utente", utente);
        return modelAndView;  
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Impossibile modificare l'utente. Dettagli errore: " + e.getMessage());
        }
    }

@DeleteMapping("/rimuoviUtenza")
@ResponseStatus(HttpStatus.NO_CONTENT)
public Object eliminaUtenza(HttpSession session) {
    // Inizializza il logger
    Logger logger = LoggerFactory.getLogger(this.getClass());

    logger.info("Inizio del metodo eliminaUtenza");

    try {
        // Controlla che la sessione contenga un utente
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente == null) {
            logger.warn("Nessun utente trovato nella sessione.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Utente non trovato nella sessione.");
        }

        logger.debug("Utente trovato: ID = {}", utente.getId());

   
        utenzaService.eliminaUtente(utente.getId());
        logger.info("Utente eliminato con successo: ID = {}", utente.getId());

        // Rimuovi l'utente dalla sessione
        session.removeAttribute("utente");
        logger.debug("Utente rimosso dalla sessione");

        // Restituisci la view della homepage
        ModelAndView modelAndView = new ModelAndView("homepage");
        logger.info("Ritorno alla view homepage");
        return modelAndView;

    } catch (RuntimeException e) {
        logger.error("Errore durante l'eliminazione dell'utente: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Impossibile eliminare l'utente. " + e.getMessage());
    }

    }
}
