package com.justInTime.service;

import com.justInTime.model.Utente;
import com.justInTime.repository.UtenzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class UtenzaService {
    private final UtenzaRepository utenzaRepository;

    public UtenteService(UtenzaRepository utenzaRepository) {
        this.utenzaRepository = utenzaRepository;
    }

    @Transactional
    public Utente creaUtente(Utente utente) {
        return utenzaRepository.save(utente);
    }

    public Utente trovaUtente(Long id) {
        return utenzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovata."));
    }

    public List<Utente> trovaTutteUtenze() {
        return utenzaRepository.findAll();
    }

    @Transactional
    public Utente aggiornaUtente(Long id, Utente utenteAggiornato) {
        Utente utente = trovaUtente(id);
        utente.setName(utenteAggiornata.getDisplayName());
        utente.setCountry(utenteAggiornata.getCountry());
        utente.setEmail(utenteAggiornata.getEmail());
        Utente.setPassword(utenteAggiornata.getPassword());
        Utente.setDataCreazioneAccount(utenteAggiornata.getDataCreazioneAccount());
        Utente.setUsername(utenteAggiornata.getUsername());
        return utenzaRepository.save(Utente);
    }

    @Transactional
    public void eliminaUtente(Long id) {
        Utente utente = trovaUtente(id);
        utenzaRepository.delete(Utente);
    }

    public Utente registerUser(Utente utente) {
        if (utenzaRepository.existsByEmail(utente.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }
        if (utenzaRepository.existsByUsername(utente.getUsername())) {
            throw new RuntimeException("Username already taken.");
        }
        utente.setDataCreazioneAccount(LocalDate.now());
        return utenzaRepository.save(Utente);
    }

    public Utente login(String usernameOrEmail, String password) {
        Utente utente = utenzaRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("User not found."));
        if (!password.equals(utente.getPassword())) {
            throw new RuntimeException("Invalid credentials.");
        }
        return Utente;
    }
}