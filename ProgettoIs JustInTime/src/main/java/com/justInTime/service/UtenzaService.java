package com.justInTime.service;

import com.justInTime.model.Utente;
import com.justInTime.repository.UtenzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    /**
     * Aggiorna l'utente con l'id specificato.
     *
     * @param id l'id dell'utente da aggiornare
     * @param utenteAggiornato l'utente con le informazioni aggiornate
     * @return l'utente aggiornato
     * @throws RuntimeException se l'utente non esiste
     */
    @Transactional
    public Utente aggiornaUtente(Long id, Utente utenteAggiornato) {
        Utente utente = trovaUtente(id);
        utente.setNome(utenteAggiornato.getName());
        utente.setPaese(utenteAggiornato.getPaese());
        utente.setEmail(utenteAggiornato.getEmail());
        utente.setPassword(utenteAggiornato.getPassword());
        utente.setUsername(utenteAggiornato.getUsername());
        return utenzaRepository.save(utente);
    }

    /**
     * Elimina l'utente con l'id specificato.
     *
     * @param id l'id dell'utente da eliminare
     * @throws RuntimeException se l'utente non esiste
     */
    @Transactional
    public void eliminaUtente(Long id) {
        Utente utente = trovaUtente(id);
        utenzaRepository.delete(utente);
    }

    /**
     * Registra un utente nuovo. Verifica che le credenziali fornite siano valide,
     * che l'email e l'username non siano già stati utilizzati e salva l'utente nel
     * database.
     *
     * @param utente l'utente da registrare
     * @param password2 la password di conferma
     * @return l'utente appena registrato
     * @throws RuntimeException se l'email o l'username sono già stati utilizzati
     */
    @Transactional
    public Utente registerUser(Utente utente, String password2) {
    
        validaUsername(utente.getUsername());
        validaNome(utente.getName());
        validaCognome(utente.getCognome());
        validaEmail(utente.getEmail());
        validaPassword(utente.getPassword(), password2);
        validaDataNascita(utente.getDataNascita());
        validaTelefono(utente.getTelefono());
        validaPaese(utente.getPaese()); 

        if (utenzaRepository.existsByEmail(utente.getEmail())) {
            throw new RuntimeException("Email già registrata.");
        }
        if (utenzaRepository.existsByUsername(utente.getUsername())) {
            throw new RuntimeException("Username già registrato.");
        }
   
        return utenzaRepository.save(utente);
    }


    /**
     * Effettua il login di un utente. Verifica che l'email o l'username e la password
     * siano valide e restituisce l'utente loggato.
     *
     * @param usernameOrEmail l'email o l'username dell'utente
     * @param password la password dell'utente
     * @return l'utente loggato
     * @throws RuntimeException se l'email o l'username non esistono o se la password
     *         non è valida
     */
    public Utente login(String usernameOrEmail, String password) {
        Utente utente = utenzaRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("User non trovato."));
        if (!password.equals(utente.getPassword())) {
            throw new RuntimeException("Credenziali non valide.");
        }
        return utente;
    }

    

    
    private void validaDataNascita(Date dataNascita) {
    if (dataNascita == null) {
        throw new RuntimeException("La data di nascita non può essere vuota.");
    }


}

private void validaTelefono(String telefono) {
    if (telefono == null || telefono.isEmpty()) {
        throw new RuntimeException("Il numero di telefono non può essere vuoto.");
    }
    String phoneRegex = "^\\+?[0-9]{10,15}$";
    if (!telefono.matches(phoneRegex)) {
        throw new RuntimeException("Formato numero di telefono non valido.");
    }
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

    private void validaCognome(String cognome) {
        if (cognome == null || cognome.isEmpty()) {
            throw new RuntimeException("Cognome non può essere vuoto.");
        }
    }


    private void validaEmail(String email) {
        if (!Pattern.matches("^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,}$", email)) {
            throw new RuntimeException("Email non valida.");
        }
    }


    private void validaPassword(String password, String password2) {
        if (!password.equals(password2)) {
            throw new RuntimeException("Le password non corrispondono."); 
        }
        if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", password)) {
            throw new RuntimeException("Password non valida. Deve contenere almeno 8 caratteri, una maiuscola, una minuscola, un numero e un carattere speciale.");
        }
  
    }

    private void validaPaese(String paese) {
        if (paese == null || paese.isEmpty()) {
            throw new RuntimeException("Il paese non può essere vuoto.");
        }
    }
}
