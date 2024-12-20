package com.justInTime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justInTime.model.Utenza;
import com.justInTime.service.UtenzaService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtenzaService utenzaService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Utenza utenza) {
        try {
            // Validate input (basic example)
            if (utenza.getEmail() == null || !utenza.getEmail().matches(".+@.+\\..+")) {
                return ResponseEntity.badRequest().body("Invalid email format.");
            }
            if (utenza.getPassword() == null || utenza.getPassword().length() < 6) {
                return ResponseEntity.badRequest().body("Password must be at least 6 characters.");
            }
            if (utenza.getUsername() == null || utenza.getUsername().isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required.");
            }

            // Register the user
            Utenza registeredUser = utenzaService.registerUser(utenza);

            // Build success response
            return ResponseEntity.ok("Registration successful! User ID: " + registeredUser.getId());

        } catch (RuntimeException e) {
            // Handle custom exceptions from the service layer
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }
}