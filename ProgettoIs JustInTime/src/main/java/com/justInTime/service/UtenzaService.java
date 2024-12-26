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

    public UtenzaService(UtenzaRepository utenzaRepository) {
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
        utente.setNome(utenteAggiornato.getVisualizzaNome());
        utente.setPaese(utenteAggiornato.getPaese());
        utente.setEmail(utenteAggiornato.getEmail());
        utente.setPassword(utenteAggiornato.getPassword());
        utente.setDataCreazioneAccount(utenteAggiornato.getDataCreazioneAccount());
        utente.setUsername(utenteAggiornato.getUsername());
        return utenzaRepository.save(utente);
    }

    @Transactional
    public void eliminaUtente(Long id) {
        Utente utente = trovaUtente(id);
        utenzaRepository.delete(utente);
    }

    public Utente registerUser(Utente utente) {
        if (utenzaRepository.existsByEmail(utente.getEmail())) {
            throw new RuntimeException("Email già resgistrata.");
        }
        if (utenzaRepository.existsByUsername(utente.getUsername())) {
            throw new RuntimeException("Username già registrato.");
        }
        utente.setDataCreazioneAccount(LocalDate.now());
        return utenzaRepository.save(utente);
    }

    public Utente login(String usernameOrEmail, String password) {
        Utente utente = utenzaRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("User non trovato."));
        if (!password.equals(utente.getPassword())) {
            throw new RuntimeException("Credenziali non valide.");
        }
        return utente;
    }
}