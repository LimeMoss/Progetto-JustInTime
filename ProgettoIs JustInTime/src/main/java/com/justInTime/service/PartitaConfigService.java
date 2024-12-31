package com.justInTime.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.model.StartGameState;
import com.justInTime.model.Utente;
import com.justInTime.repository.PartitaRepository;
import com.justInTime.repository.PlayerRepository;

import jakarta.transaction.Transactional;

@Service
public class PartitaConfigService {

    @Autowired
    PartitaRepository partitaRepository;

    @Autowired
    UtenzaService utenzaService;

    @Autowired 
    PlayerService playerService;

    @Autowired 
    PlayerRepository playerRepository;


    private List<Player> giocatoriInConfigurazione = new ArrayList<>();


    public void aggiungiGiocatoreConfig(String usernameOrEmail, String password) {
    
        if (giocatoriInConfigurazione.size() >= 4) {
            throw new IllegalArgumentException("Non è possibile aggiungere più di 4 giocatori.");
        }
    
     
        for (Player giocatore : giocatoriInConfigurazione) {
            if (giocatore.getName().equals(usernameOrEmail) || giocatore.getUtente().getEmail().equals(usernameOrEmail)) {
                throw new IllegalArgumentException("Il giocatore è già stato aggiunto.");
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

        if (e.getMessage().contains("Giocatore non trovato")) {
            nuovoGiocatore = playerService.creaGiocatore(usernameOrEmail, utente.getId());
        } else {
            throw e; 
        }
    }
    giocatoriInConfigurazione.add(nuovoGiocatore);
    

    }
    public void aggiungiGiocatoreConfigNOLOGIN(Player player) {
    
        if (giocatoriInConfigurazione.size() >= 4) {
            throw new IllegalArgumentException("Non è possibile aggiungere più di 4 giocatori.");
        }
    
        // Controllo per evitare duplicati di giocatori (stesso nome o email)
        for (Player giocatore : giocatoriInConfigurazione) {
            if (giocatore.getName().equals(player.getName())) {
                throw new IllegalArgumentException("Il giocatore è già stato aggiunto.");
            }
        }
        

    
    
        giocatoriInConfigurazione.add(player);
    }
    
    


    public void rimuoviGiocatore() {
        if(giocatoriInConfigurazione.size() > 1){
        //playerService.deletePlayer(giocatoriInConfigurazione.getLast().getId());
        giocatoriInConfigurazione.removeLast();
        }
        else throw  new IllegalArgumentException("Non ci sono giocatori da rimuovere.");
    }

    @Transactional
public Partita creaPartita() {
    if (giocatoriInConfigurazione.size() < 2 || giocatoriInConfigurazione.size() > 4) {
        throw new IllegalArgumentException("Devono esserci almeno due giocatori.");
    }

    Partita partita = new Partita();


    for (Player giocatore : giocatoriInConfigurazione) {

        partita.getGiocatori().add(giocatore);

        giocatore.getPartite().add(partita);
    }


    partita.setGameState(new StartGameState());


    partitaRepository.save(partita);

    for (Player giocatore : giocatoriInConfigurazione) {
        playerRepository.save(giocatore);
    }


    giocatoriInConfigurazione.clear();

    return partita;
}


    public List<String> getGiocatoriInConfigurazione() {
        List<String> nomiGiocatori = new ArrayList<>();
        for (Player giocatore : giocatoriInConfigurazione) {
            nomiGiocatori.add(giocatore.getName());  
        }
        return nomiGiocatori;
    }


    @Transactional
    public Player aggiungiGiocatoreAPartita(Long playerId, Long partitaId) {
        Player player = playerService.trovaGiocatore(playerId);
        Partita partita = partitaRepository.findById(partitaId)
                .orElseThrow(() -> new RuntimeException("Partita non trovata."));
        
        // Verifica se il giocatore è già nella partita
        if (!partita.getGiocatori().contains(player)) {
            partita.getGiocatori().add(player);
            player.getPartite().add(partita);
            partitaRepository.save(partita);
        }
        
        return playerRepository.save(player);
    }

    @Transactional
    public Player rimuoviGiocatoreDaPartita(Long playerId, Long partitaId) {
        Player player = playerService.trovaGiocatore(playerId);
        Partita partita = partitaRepository.findById(partitaId)
                .orElseThrow(() -> new RuntimeException("Partita non trovata."));
        
        partita.getGiocatori().remove(player);
        player.getPartite().remove(partita);
        partitaRepository.save(partita);
        
        return playerRepository.save(player);
    }
    @Transactional
    public boolean isGiocatoreInPartita(Long playerId, Long partitaId) {
        Player player = playerService.trovaGiocatore(playerId);
        return player.getPartite().stream()
                .anyMatch(partita -> partita.getId().equals(partitaId));
    }
    
}