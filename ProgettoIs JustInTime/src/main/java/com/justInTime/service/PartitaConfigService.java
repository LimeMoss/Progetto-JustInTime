package com.justInTime.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import com.justInTime.model.GameState;
import com.justInTime.model.Partita;

import com.justInTime.model.Player;
import com.justInTime.model.Utente;
import com.justInTime.repository.PartitaRepository;


import jakarta.servlet.http.HttpSession;

import jakarta.transaction.Transactional;

@Service
public class PartitaConfigService {

    @Autowired
    private PartitaRepository partitaRepository;

    @Autowired
    private UtenzaService utenzaService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PartitaService partitaService;

    @Autowired
    @Qualifier("startGameState")
    private GameState startGameState;

    private List<Player> giocatoriInConfigurazione = new ArrayList<Player>();

    /**
     * Aggiunge un giocatore alla configurazione corrente.
     * La configurazione corrente contiene i giocatori che saranno coinvolti
     * nella partita.
     * 
     * @param usernameOrEmail l'username o l'email del giocatore da aggiungere
     * @param password        la password del giocatore da aggiungere
     * @throws IllegalArgumentException se il giocatore non esiste o se la password
     *                                  non è corretta o se il giocatore è già stato
     *                                  aggiunto o se il numero di giocatori da
     *                                  aggiungere supera il massimo consentito (4)
     */

    public void aggiungiGiocatoreConfig(String usernameOrEmail, String password, HttpSession session) {
        // Verifica lo stato del flag di sessione (SessionListener)
        Boolean sessionListener = (Boolean) session.getAttribute("SessionListener");

        if (sessionListener == null || !sessionListener) {
            if (giocatoriInConfigurazione != null) {
                giocatoriInConfigurazione.clear();

            }

        }

        Utente SessionUser = (Utente) session.getAttribute("utente");

        if (SessionUser == null) {
            throw new IllegalStateException("Utente non presente nella sessione.");
        }

        // Verifica se l'utente sta cercando di aggiungere se stesso
        if (usernameOrEmail.equals(SessionUser.getUsername())) {
            throw new IllegalArgumentException("L'utente è già stato aggiunto");
        }

        if (giocatoriInConfigurazione != null) {
            if (giocatoriInConfigurazione.size() > 3) {
                throw new IllegalArgumentException("Non è possibile aggiungere più di 4 giocatori.");
            }

            for (Player giocatore : giocatoriInConfigurazione) {
                if (giocatore.getUtente().getUsername().equals(usernameOrEmail) ||
                        giocatore.getUtente().getEmail().equals(usernameOrEmail)) {
                    throw new IllegalArgumentException("Il giocatore è già stato aggiunto.");
                }
            }
        }

        Utente utente = utenzaService.login(usernameOrEmail, password);
        if (utente == null) {
            throw new IllegalArgumentException("Credenziali non valide.");
        }

        Player nuovoGiocatore = null;

        try {
            nuovoGiocatore = playerService.trovaGiocatore(utente.getId());
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Giocatore non trovato.")) {
                nuovoGiocatore = playerService.creaGiocatore(utente.getId());
            } else {
                throw e;
            }
        }

        giocatoriInConfigurazione.add(nuovoGiocatore);

        session.setAttribute("SessionListener", true);
    }

    /**
     * Aggiunge un giocatore alla configurazione corrente senza eseguire il login.
     * La configurazione corrente contiene i giocatori che saranno coinvolti
     * nella partita.
     * 
     * @param player il giocatore da aggiungere
     * @throws IllegalArgumentException se il giocatore non esiste o se il
     *                                  giocatore è già stato aggiunto o se il
     *                                  numero di giocatori da aggiungere supera
     *                                  il massimo consentito (4)
     */
    public void aggiungiGiocatoreConfigNOLOGIN(Player player) {

        if (giocatoriInConfigurazione.size() >= 4) {
            throw new IllegalArgumentException("Non è possibile aggiungere più di 4 giocatori.");
        }

        for (Player giocatore : giocatoriInConfigurazione) {
            if (giocatore.getUtente().getUsername().equals(player.getUtente().getUsername())) {
                throw new IllegalArgumentException("Il giocatore è già stato aggiunto.");
            }
        }

        giocatoriInConfigurazione.addFirst(player);
    }

    /**
     * Rimuove l'ultimo giocatore aggiunto alla configurazione corrente.
     * La configurazione corrente contiene i giocatori che saranno coinvolti
     * nella partita.
     * 
     * @throws IllegalArgumentException se non ci sono giocatori da rimuovere
     */
    public String rimuoviGiocatore(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utente");
        if ((giocatoriInConfigurazione != null) && (giocatoriInConfigurazione.getLast().getId() != utente.getId())) {
            // playerService.deletePlayer(giocatoriInConfigurazione.getLast().getId());
            Player player = giocatoriInConfigurazione.removeLast();
            return player.getUtente().getUsername();
        } else
            throw new IllegalArgumentException("Non ci sono giocatori da rimuovere.");
    }

    /**
     * Crea una partita con i giocatori attualmente in configurazione e li
     * associa alla partita.
     * La partita viene salvata e lo stato della partita viene impostato su
     * "inizio partita".
     * La configurazione dei giocatori viene ripulita.
     * 
     * @param session la sessione HTTP
     * @return la partita creata
     * @throws IllegalArgumentException se il numero di giocatori
     *                                  non è compreso tra 2 e 4
     */
    public Partita creaPartita(HttpSession session) {
        Utente primoUtente = (Utente) session.getAttribute("utente");
        AssociazionePlayerUtenzaSession(primoUtente);

        aggiungiGiocatoreConfigNOLOGIN(primoUtente.getPlayer());

        if (giocatoriInConfigurazione.size() < 2 || giocatoriInConfigurazione.size() > 4) {
            throw new IllegalArgumentException("Devono esserci almeno due giocatori.");
        }

        Partita partita = new Partita();

        for (Player giocatore : giocatoriInConfigurazione) {

            giocatore = playerService.savePlayer(giocatore.getId());

            partita.getGiocatori().add(giocatore);
            giocatore.getPartite().add(partita);
        }

        partita = partitaRepository.save(partita);

        partitaService.setsGameState(partita.getId(), startGameState);

        giocatoriInConfigurazione.clear();

        return partita;
    }

    /**
     * Restituisce una lista dei nomi dei giocatori attualmente
     * in configurazione per la partita.
     *
     * @return una lista di stringhe contenente i nomi dei giocatori
     *         in configurazione.
     */
    public List<String> getGiocatoriInConfigurazione(HttpSession session) {

        List<String> nomiGiocatori = new ArrayList<>();
        for (Player giocatore : giocatoriInConfigurazione) {
            if (giocatore != null && giocatore.getUtente() != null) {
                nomiGiocatori.add(giocatore.getUtente().getUsername());
            }
        }
        return nomiGiocatori;
    }

    /**
     * Aggiunge un giocatore ad una partita.
     * Verifica se il giocatore è già nella partita e se non lo è, lo aggiunge.
     * La partita e il giocatore vengono salvati.
     * 
     * @param playerId  l'id del giocatore da aggiungere
     * @param partitaId l'id della partita
     * @return il giocatore aggiunto
     * @throws RuntimeException se la partita non esiste
     */
    @Transactional
    public Player aggiungiGiocatoreAPartita(Long playerId, Long partitaId) {
        Player player = playerService.trovaGiocatore(playerId);
        Partita partita = partitaRepository.findById(partitaId)
                .orElseThrow(() -> new RuntimeException("Partita non trovata."));

        if (!partita.getGiocatori().contains(player)) {
            partita.getGiocatori().add(player);
            player.getPartite().add(partita);
            partitaRepository.save(partita);
        }

        return playerService.savePlayer(player.getId());
    }

    /**
     * Rimuove un giocatore da una partita.
     * Verifica se il giocatore è nella partita e se lo è, lo rimuove.
     * La partita e il giocatore vengono salvati.
     * 
     * @param playerId  l'id del giocatore da rimuovere
     * @param partitaId l'id della partita
     * @return il giocatore rimosso
     * @throws RuntimeException se la partita non esiste
     */
    @Transactional
    public Player rimuoviGiocatoreDaPartita(Long playerId, Long partitaId) {
        Player player = playerService.trovaGiocatore(playerId);
        Partita partita = partitaRepository.findById(partitaId)
                .orElseThrow(() -> new RuntimeException("Partita non trovata."));

        partita.getGiocatori().remove(player);
        player.getPartite().remove(partita);
        partitaRepository.save(partita);

        return playerService.savePlayer(player.getId());
    }

    /**
     * Verifica se un giocatore è in una partita.
     * L'operazione viene eseguita in modo atomic.
     * 
     * @param playerId  l'ID del giocatore da verificare
     * @param partitaId l'ID della partita
     * @return true se il giocatore è nella partita, false altrimenti
     * @throws RuntimeException se il giocatore non esiste
     */
    @Transactional
    public boolean isGiocatoreInPartita(Long playerId, Long partitaId) {
        Player player = playerService.trovaGiocatore(playerId);
        return player.getPartite().stream()
                .anyMatch(partita -> partita.getId().equals(partitaId));
    }

    public void AssociazionePlayerUtenzaSession(Utente primoUtente) {
        try {

            playerService.trovaGiocatore(primoUtente.getId());

        } catch (RuntimeException e) {

            if (e.getMessage().contains("Giocatore non trovato")) {

                playerService.creaGiocatore(primoUtente.getId());
            } else {
                throw e;
            }
        }

    }

    @Transactional
    public Partita creaNuovaPartitaDaPartitaPrecedente(Long partitaId) {
        Partita partitaPrecedente = partitaRepository.findById(partitaId)
                .orElseThrow(() -> new RuntimeException("Partita non trovata nella creazione da partita precedente."));

        Partita nuovaPartita = new Partita();

        partitaService.setsGameState(nuovaPartita.getId(), startGameState);
        ;

        for (Player giocatore : partitaPrecedente.getGiocatori()) {
            nuovaPartita.getGiocatori().add(giocatore);
            giocatore.getPartite().add(nuovaPartita);
        }

        partitaRepository.save(nuovaPartita);
        for (Player giocatore : nuovaPartita.getGiocatori()) {
            playerService.savePlayer(giocatore.getId());
        }

        return nuovaPartita;
    }

}