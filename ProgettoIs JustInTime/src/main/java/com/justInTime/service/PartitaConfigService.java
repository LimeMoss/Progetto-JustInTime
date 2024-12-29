package com.justInTime.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justInTime.model.Achievements;
import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.model.Utente;
import com.justInTime.repository.PartitaRepository;

@Service
public class PartitaConfigService {
    @Autowired
    private PartitaRepository partitaRepository;
    @Autowired
    UtenzaService utenzaService;
    @Autowired 
    PlayerService playerService;


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
    
    


    public void rimuoviGiocatore() {
        if(giocatoriInConfigurazione.size() > 1){
        //playerService.deletePlayer(giocatoriInConfigurazione.getLast().getId());
        giocatoriInConfigurazione.removeLast();
        }
        else throw  new IllegalArgumentException("Non ci sono giocatori da rimuovere.");
    }

    public Partita creaPartita() {
        if (giocatoriInConfigurazione.size() < 2 || giocatoriInConfigurazione.size()>4) {
            throw new IllegalArgumentException("Devono esserci almeno due giocatori.");
        }

        Partita partita = new Partita();
        Map<Player, List<Achievements>>playerAchievement = partita.getPlayerAchievements();
        for (Player giocatore : giocatoriInConfigurazione) {
            playerAchievement.putIfAbsent(giocatore, new ArrayList<>());
        }

        partita.setGiocatori(new ArrayList<>(giocatoriInConfigurazione));

        Partita partitaSalvata = partitaRepository.save(partita);

        giocatoriInConfigurazione.clear();
        

        return partitaSalvata;

    }


    public List<String> getGiocatoriInConfigurazione() {
        List<String> nomiGiocatori = new ArrayList<>();
        for (Player giocatore : giocatoriInConfigurazione) {
            nomiGiocatori.add(giocatore.getName());  
        }
        return nomiGiocatori;
    }


    
    
}