package com.justInTime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justInTime.model.Partita;
import com.justInTime.model.Player;
import com.justInTime.repository.PartitaRepository;

@Service
public class PartitaConfigService{
    @Autowired
    private PartitaRepository partitaRepository;
    private PartitaService partitaService;
    
    public Partita createPartita() {
        Partita partita = new Partita();
        return partitaRepository.save(partita);
    }

    
    public Partita aggiungiGiocatore(Long partitaId, Player giocatore) {
        Partita partita = partitaService.getPartita(partitaId);
        if (partita.getGiocatori().size() < 4 && !partita.getGiocatori().contains(giocatore)) {
            partita.getGiocatori().add(giocatore);
            return partitaRepository.save(partita);
        }
        throw new RuntimeException("Impossibile aggiungere il giocatore");
    }

    public void rimuoviGiocatore(Long partitaId, Long giocatoreId) {
        Partita partita =  partitaService.getPartita(partitaId);
        partita.getGiocatori().removeIf(g -> g.getId().equals(giocatoreId));
        partitaRepository.save(partita);
    }


    
}