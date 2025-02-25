package com.justInTime.service;

import com.justInTime.DTO.FullPlayerDataDTO;
import com.justInTime.DTO.FullPlayerDataDTOPsw;
import com.justInTime.DTO.paeseUtenzaDTO;
import com.justInTime.model.Player;
import com.justInTime.model.Utente;
import com.justInTime.repository.UtenzaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UtenzaService {
    @Autowired
    private UtenzaRepository utenzaRepository;

    @Autowired
    private PlayerService playerService;
   



    /**
     * Crea un nuovo utente e lo salva nel database.
     *
     * @param utente l'utente da creare
     * @return l'utente appena creato
     */
    @Transactional
    public Utente creaUtente(Utente utente) {
        return utenzaRepository.save(utente);
    }


    /**
     * Trova l'utente con l'id specificato.
     *
     * @param id l'id dell'utente da cercare
     * @return l'utente cercato
     * @throws RuntimeException se l'utente non esiste
     */
    public Utente trovaUtente(Long id) {
        return utenzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utente non trovata."));
    }

     public paeseUtenzaDTO trovaUtentePaese(Long id) {

        Utente utente= utenzaRepository.findById(id).orElseThrow(() -> new RuntimeException("Utente non trovata."));
        paeseUtenzaDTO utenteDTO = new paeseUtenzaDTO(utente.getPaese(), utente.getUsername());
        return utenteDTO;

    }

    public FullPlayerDataDTOPsw trovaUtenteConPsw(Long id) {

        Utente utente= utenzaRepository.findById(id).orElseThrow(() -> new RuntimeException("Utente non trovata."));
        FullPlayerDataDTOPsw utenteDTO = new FullPlayerDataDTOPsw(utente.getUsername(), utente.getName(), utente.getCognome(), utente.getEmail(), utente.getPassword(), utente.getTelefono(), utente.getPaese(), utente.getDataNascita());



        return utenteDTO;

    }
    /**
     * Trova l'utente con l'id specificato e restituisce un oggetto
     * FullPlayerDataDTO contenente le informazioni dell'utente senza la password.
     *
     * @param id l'id dell'utente da cercare
     * @return l'utente cercato
     * @throws RuntimeException se l'utente non esiste
     */
    public FullPlayerDataDTO trovaUtenteNoPsw(Long id) {

        Utente utente= utenzaRepository.findById(id).orElseThrow(() -> new RuntimeException("Utente non trovata."));
        FullPlayerDataDTO utenteDTO = new FullPlayerDataDTO(utente.getUsername(), utente.getName(), utente.getCognome(), utente.getEmail(), utente.getTelefono(), utente.getPaese(), utente.getDataNascita());



        return utenteDTO;

    }


    /**
     * Ritorna la lista di tutti gli utenti presenti nel database.
     *
     * @return la lista di tutti gli utenti
     */
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
    public Utente aggiornaUtente(Long id, Utente utenteAggiornato, String password2) {
        Utente utente = trovaUtente(id);
    
  
        validaUsername(utenteAggiornato.getUsername());
        validaNome(utenteAggiornato.getName());
        validaCognome(utenteAggiornato.getCognome());
        validaEmail(utenteAggiornato.getEmail());
        validaPassword(utenteAggiornato.getPassword(), password2);
        validaDataNascita(utenteAggiornato.getDataNascita());
        validaTelefono(utenteAggiornato.getTelefono());
        validaPaese(utenteAggiornato.getPaese());
    

        if (!utente.getEmail().equals(utenteAggiornato.getEmail()) 
                && utenzaRepository.existsByEmail(utenteAggiornato.getEmail())) {
            throw new RuntimeException("Email già registrata.");
        }

        if (!utente.getUsername().equals(utenteAggiornato.getUsername()) 
                && utenzaRepository.existsByUsername(utenteAggiornato.getUsername())) {
            throw new RuntimeException("Username già registrato.");
        }

        if (!utente.getName().equals(utenteAggiornato.getName())) {
            utente.setNome(utenteAggiornato.getName());
        }
        if (!utente.getCognome().equals(utenteAggiornato.getCognome())) {
            utente.setCognome(utenteAggiornato.getCognome());
        }
        if (!utente.getPaese().equals(utenteAggiornato.getPaese())) {
            utente.setPaese(utenteAggiornato.getPaese());
        }
        if (!utente.getEmail().equals(utenteAggiornato.getEmail())) {
            utente.setEmail(utenteAggiornato.getEmail());
        }
        if (!utente.getPassword().equals(utenteAggiornato.getPassword())) {
            utente.setPassword(utenteAggiornato.getPassword());
        }
        if (!utente.getUsername().equals(utenteAggiornato.getUsername())) {
            utente.setUsername(utenteAggiornato.getUsername());
        }
        if (!utente.getDataNascita().equals(utenteAggiornato.getDataNascita())) {
            utente.setDataNascita(utenteAggiornato.getDataNascita());
        }
        if (!utente.getTelefono().equals(utenteAggiornato.getTelefono())) {
            utente.setTelefono(utenteAggiornato.getTelefono());
        }
    
  
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
        
        // Controlla se il Player esiste
        if (utente.getPlayer() != null) {
            // Salva esplicitamente il Player se non è già persistente
            playerService.savePlayer(utente.getPlayer().getId());  // Assicurati che savePlayer salvi correttamente il Player
        }
        
        // Elimina il Player associato
     

        // Elimina l'Utente
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
            throw new IllegalArgumentException("Credenziali non valide.");
        }
        return utente;
    }



    /**
     * Verifica che la data di nascita non sia vuota.
     *
     * @param dataNascita la data di nascita da verificare
     * @throws IllegalArgumentException se la data di nascita non è valida
     */
    private void validaDataNascita(Date dataNascita) {
        if (dataNascita == null) {
            throw new IllegalArgumentException("La data di nascita non può essere vuota.");
        }


    }


/**
 * Verifica che il numero di telefono non sia vuoto e rispetti il formato
 * internazionale. Il formato richiesto è: +CCC NNN NNN NNNN, dove CCC è il
 * prefisso del paese con uno a tre cifre, seguito da tre gruppi di numeri.
 *
 * @param telefono il numero di telefono da verificare
 * @throws IllegalArgumentException se il numero di telefono è vuoto o non rispetta
 *         il formato richiesto
 */

    private void validaTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("Il numero di telefono non può essere vuoto.");
        }
        String phoneRegex = "^\\+[0-9]{1,3}\\s[0-9]{3}\\s[0-9]{3}\\s[0-9]{4}$";
        if (!telefono.matches(phoneRegex)) {
            throw new IllegalArgumentException("Formato numero di telefono non valido.");
        }
    }



/**
 * Verifica che l'username sia valido. Deve essere lungo tra 2 e 30 caratteri
 * e non deve contenere caratteri speciali.
 *
 * @param username l'username da verificare
 * @throws IllegalArgumentException se l'username non è valido
 */

    private void validaUsername(String username) {
        if (!Pattern.matches("^[A-z0-9]{2,30}$", username)) {
            throw new IllegalArgumentException("Username non valido. Deve essere tra 2 e 30 caratteri senza caratteri speciali.");
        }
    }


    /**
     * Verifica che il nome sia valido. Deve essere lungo tra 2 e 30 caratteri
     * e non deve contenere caratteri speciali.
     *
     * @param nome il nome da verificare
     * @throws IllegalArgumentException se il nome non è valido
     */
    private void validaNome(String nome) {
        if (!Pattern.matches("^[A-zÀ-ù ‘-]{2,30}$", nome)) {
            throw new IllegalArgumentException("Nome non valido. Deve essere tra 2 e 30 caratteri senza caratteri speciali.");
        }
    }

    /**
     * Verifica che il cognome sia valido. Deve essere lungo tra 2 e 30 caratteri
     * e non deve contenere caratteri speciali.
     *
     * @param cognome il cognome da verificare
     * @throws IllegalArgumentException se il cognome non è valido
     */

    private void validaCognome(String cognome) {
        if (!Pattern.matches("^[A-zÀ-ù ‘-]{2,30}$", cognome)) {
            throw new IllegalArgumentException("Cognome non valido. Deve essere tra 2 e 30 caratteri senza caratteri speciali.");
        }
    }


    /**
     * Verifica che l'email sia valida. Deve rispettare il formato standard
     * delle email, che include un insieme di caratteri seguiti da un '@',
     * un dominio e un suffisso di dominio.
     *
     * @param email l'email da verificare
     * @throws IllegalArgumentException se l'email non è valida
     */

    private void validaEmail(String email) {
        if (!Pattern.matches("^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,}$", email)) {
            throw new IllegalArgumentException("Email non valida.");
        }
    }


    /**
     * Verifica che le due password coincidano e che siano valide.
     *
     * Una password è valida se:
     * - contiene almeno 8 caratteri
     * - contiene almeno una lettera minuscola
     * - contiene almeno una lettera maiuscola
     * - contiene almeno un numero
     * - contiene almeno un carattere speciale (@$!%*?&)
     *
     * @param password la prima password
     * @param password2 la seconda password
     * @throws IllegalArgumentException se le password non corrispondono o non sono valide
     */
    private void validaPassword(String password, String password2) {
        if (!password.equals(password2)) {
            throw new IllegalArgumentException("Le password non corrispondono.");
        }
        if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", password)) {
            throw new IllegalArgumentException("Password non valida. Deve contenere almeno 8 caratteri, una maiuscola, una minuscola, un numero e un carattere speciale.");
        }

    }

    /**
     * Verifica che il paese non sia vuoto.
     *
     * @param paese il paese da verificare
     * @throws IllegalArgumentException se il paese è vuoto
     */
    private void validaPaese(String paese) {
        if (paese == null || paese.isEmpty()) {
            throw new IllegalArgumentException("Il paese non può essere vuoto.");
        }
    }
}
