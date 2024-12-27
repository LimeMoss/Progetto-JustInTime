package com.justInTime.service;

import com.justInTime.model.Utente;
import com.justInTime.repository.UtenzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

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

    @Transactional
    public Utente registerUser(Utente utente) {
        
        validaUsername(utente.getUsername());
        validaNome(utente.getVisualizzaNome());
        validaEmail(utente.getEmail());
        validaPassword(utente.getPassword());

       
        if (utenzaRepository.existsByEmail(utente.getEmail())) {
            throw new RuntimeException("Email già registrata.");
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


    private void validaUsername(String username) {
        if (!Pattern.matches("^[A-z0-9]{2,30}$", username)) {
            throw new RuntimeException("Username non valido. Deve essere tra 2 e 30 caratteri senza caratteri speciali.");
        }
    }


    private void validaNome(String nome) {
        if (!Pattern.matches("^[A-zÀ-ù ‘-]{2,30}$", nome)) {
            throw new RuntimeException("Nome non valido. Deve essere tra 2 e 30 caratteri senza caratteri speciali.");
        }
    }


    private void validaEmail(String email) {
        if (!Pattern.matches("^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,}$", email)) {
            throw new RuntimeException("Email non valida.");
        }
    }


    private void validaPassword(String password) {
        if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", password)) {
            throw new RuntimeException("Password non valida. Deve contenere almeno 8 caratteri, una maiuscola, una minuscola, un numero e un carattere speciale.");
        }
    }
}
