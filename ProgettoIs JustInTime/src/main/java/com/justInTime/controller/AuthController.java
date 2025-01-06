package com.justInTime.controller;

import com.justInTime.model.Utente;
import com.justInTime.service.UtenzaService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/")
public class AuthController {
        @Autowired
    private UtenzaService utenzaService;


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
     * @param password        la password dell'utente
     * @param session         la sessione http
     * @return la pagina di reindirizzamento. Se l'utente non esiste o se la password
     * non &egrave; valida, reindirizza alla pagina di login con un
     * parametro di query string "error" impostato a "true"
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String usernameOrEmail, @RequestParam String password, HttpSession session) {
        try {
            // Effettua il login utilizzando il servizio
            Utente utente = utenzaService.login(usernameOrEmail, password);

            // Salva l'utente nella sessione
            session.setAttribute("utente", utente);

            // Risposta di successo
            return ResponseEntity.ok(Map.of("message", "Login effettuato con successo."));
        } catch (RuntimeException e) {
            // Gestione degli errori
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Credenziali non valide."));
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
      public ModelAndView logout(HttpSession session) {
      
        if (session != null) {
            session.invalidate(); // Invalida la sessione
        }
        return new ModelAndView("login");
    }

    @PostMapping("/resetIsPageOpen")
    public String resetIsPageOpen(HttpSession session) {

    session.removeAttribute("IsPageOpen");
    return "success";  
}
}
