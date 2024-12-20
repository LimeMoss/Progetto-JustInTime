package com.justInTime.service;

import com.justInTime.model.Utenza;
import com.justInTime.repository.UtenzaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Service
public class UtenzaService {

    private final UtenzaRepository utenzaRepository;

    public UtenzaService(UtenzaRepository utenzaRepository) {
        this.utenzaRepository = utenzaRepository;
    }

    // Crea una nuova utenza
    @Transactional
    public Utenza creaUtenza(Utenza utenza) {
        return utenzaRepository.save(utenza);
    }

    // Trova un'utenza per ID
    public Utenza trovaUtenza(Long id) {
        return utenzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utenza non trovata."));
    }

    // Trova tutte le utenze
    public List<Utenza> trovaTutteUtenze() {
        return utenzaRepository.findAll();
    }

    // Aggiorna i dettagli di un'utenza
    @Transactional
    public Utenza aggiornaUtenza(Long id, Utenza utenzaAggiornata) {
        Utenza utenza = trovaUtenza(id);
        utenza.setNome(utenzaAggiornata.getName());
        utenza.setCognome(utenzaAggiornata.getCognome());
        utenza.setPaese(utenzaAggiornata.getPaese());
        utenza.setTelefono(utenzaAggiornata.getTelefono());
        utenza.setEmail(utenzaAggiornata.getEmail());
        utenza.setPassword(utenzaAggiornata.getPassword());
        utenza.setDataCreazioneAccount(utenzaAggiornata.getDataCreazioneAccount());
        utenza.setUsername(utenzaAggiornata.getUsername());
        return utenzaRepository.save(utenza);
    }

    // Elimina un'utenza per ID
    @Transactional
    public void eliminaUtenza(Long id) {
        Utenza utenza = trovaUtenza(id);
        utenzaRepository.delete(utenza);
    }



    public Utenza registerUser(Utenza utenza) {
        // Check if email or username already exists
        if (utenzaRepository.existsByEmail(utenza.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }
        if (utenzaRepository.existsByUsername(utenza.getUsername())) {
            throw new RuntimeException("Username already taken.");
        }

        // Encrypt the password before saving
        utenza.setPassword((utenza.getPassword()));

        // Set account creation date
        utenza.setDataCreazioneAccount(LocalDate.now());

        return utenzaRepository.save(utenza);
    }

    public Utenza login(String usernameOrEmail, String password) {
        // Retrieve the user by username or email
        Utenza utenza = utenzaRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("User not found."));

        // Validate the password
        if (!(password == null ? utenza.getPassword() == null : password.equals(utenza.getPassword()))) {
            throw new RuntimeException("Invalid credentials.");
        }

        // Return the authenticated user
        return utenza;
    }
}
