package com.justInTime.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.repository.PartitaRepository;

@Service
public class PartitaConfigService {
    @Autowired
    private PartitaRepository partitaRepository;


    private List<Player> giocatoriInConfigurazione = new ArrayList<>();


    public void aggiungiGiocatore(String usernameOrEmail, String password) {  {
        if (giocatoriInConfigurazione.size() >= 4) {
            throw new IllegalArgumentException("Non è possibile aggiungere più di 4 giocatori.");
        }
        /* 
        for (Player giocatore : giocatoriInConfigurazione) {
            if (giocatore.getName().equals(usernameOrEmail)) {
                throw new IllegalArgumentException("Il giocatore è già stato aggiunto.");
            }
            if (giocatore.getEmail().equals(usernameOrEmail)) {
                throw new IllegalArgumentException("Il giocatore è già stato aggiunto.");
            }
        }
            */

        //TODO BRIDGE PATTERN//
        /* 
       UtenzaService utenzaService;
        giocatoriInConfigurazione.add(utenzaService.login(usernameOrEmail, password));
        */
    }
    }


    public void rimuoviGiocatore(Player giocatore) {
        giocatoriInConfigurazione.remove(giocatore);
    }

    public Partita creaPartita() {
        if (giocatoriInConfigurazione.size() < 2 || giocatoriInConfigurazione.size()>4) {
            throw new IllegalArgumentException("Devono esserci almeno due giocatori.");
        }

        Partita partita = new Partita();
        partita.setGiocatori(new ArrayList<>(giocatoriInConfigurazione));

        Partita partitaSalvata = partitaRepository.save(partita);

        giocatoriInConfigurazione.clear();

        return partitaSalvata;
    }


    public List<Player> getGiocatoriInConfigurazione() {
        return new ArrayList<>(giocatoriInConfigurazione);
    }
    
}