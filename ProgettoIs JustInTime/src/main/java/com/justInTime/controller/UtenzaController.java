package com.justInTime.controller;

import com.justInTime.DTO.FullPlayerDataDTO;
import com.justInTime.DTO.FullPlayerDataDTOPsw;
import com.justInTime.DTO.paeseUtenzaDTO;
import com.justInTime.model.Utente;

import com.justInTime.service.UtenzaService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

        try{
        Utente utente = (Utente)session.getAttribute("utente");

        ModelAndView modelAndView = new ModelAndView("homepage"); 
        
        utenzaService.eliminaUtente(utente.getId());


        session.removeAttribute("utente");
        return modelAndView;
    } catch(RuntimeException e){
              return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Impossibile eliminare l'utente.");
    }
    }
}
