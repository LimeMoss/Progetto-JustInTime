package com.justInTime.controller;

import com.justInTime.model.Utente;
import com.justInTime.service.UtenzaService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AuthController {

    private final UtenzaService utenzaService;

    public AuthController(UtenzaService utenzaService) {
        this.utenzaService = utenzaService;
    }

    /**
     * Registra un nuovo utente e reindirizza alla pagina di login
     *
     * @param utente      l'utente da registrare
     * @param password2   la password di conferma
     * @return            response entity contenente la pagina di login
     * @throws            RuntimeException se la registrazione non riesce
     */
    @PostMapping("/registrazione")
    public ResponseEntity<?> registraUtente(@RequestBody Utente utente, @RequestParam String password2) {
        try {
            utenzaService.registerUser(utente, password2);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registrazione completata con successo");
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage(); // Ottieni il messaggio dell'errore
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }
    


    /**
     * Effettua il login di un utente. Verifica che l'email o l'username e la password
     * siano valide e salva l'utente nella sessione.
     *
     * @param usernameOrEmail l'email o l'username dell'utente
     * @param password la password dell'utente
     * @param session la sessione http
     * @return la pagina di reindirizzamento. Se l'utente non esiste o se la password
     *         non &egrave; valida, reindirizza alla pagina di login con un
     *         parametro di query string "error" impostato a "true"
     */
    @PostMapping("/login")
    public String login(@RequestParam String usernameOrEmail, @RequestParam String password, HttpSession session) {
        try {
       
            Utente utente = utenzaService.login(usernameOrEmail, password);

    
            session.setAttribute("utente", utente);


            return "redirect:/homepage";
        } catch (RuntimeException e) {

        return "redirect:/login?error=true";
    }

        
    }

    

    /**
     * Invalida la sessione corrente e reindirizza l'utente alla pagina di
     * login.
     *
     * @param session la sessione http
     * @return la pagina di reindirizzamento
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();
        return "redirect:/login"; 
    }
}
