package com.justInTime.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.justInTime.service.PartitaService;
import com.justInTime.service.PlayerService;

/**
 * Classe che rappresenta lo stato finale di una partita.
 * Implementa l'interfaccia {@link GameState} e gestisce le operazioni
 * da eseguire quando una partita termina.
 */
@Component("endGameState")
public class EndGameState implements GameState {
    private Partita tpartita = new Partita();
    @Autowired
    private PartitaService partitaService;
    
    @Autowired
    private PlayerService playerService;

    /**
     * Esegue le operazioni necessarie al termine di una partita.
     * Utilizza {@link PartitaService}.
     *
     * @param partita La partita corrente da terminare.
     */
  @Override
public void execute(Partita partita) {
    tpartita = partita;
    List<Player> players = new ArrayList<>(partita.getGiocatori());  
    for (int i = 0; i < players.size(); i++) {
        Player player = players.get(i);
        
        if (partita.getIndiceGiocatoreCorrente() == i) {
            playerService.addVictory(player.getId());
        } else {
            playerService.addScore(player);
        }
    }
    
    partitaService.terminaPartita(partita);
}

    /**
     * Esegue le operazioni necessarie al termine di una partita.
     * Utilizza {@link PartitaService}.
     * 
     * @param partita La partita corrente da terminare.
     * @param arg     L'argomento che rappresenta lo stato di fine partita.
     *                Valori accettati: "TempoTerminato"
     */
    public void execute(Partita partita, String arg) {
        if (arg.equals("TempoTerminato")) {
            List<Player> players = partita.getGiocatori();
            
            for (Player player : players) {
                if (!player.isEscluso()) {
              
                    playerService.addVictory(player.getId());
                } else {
      
                    playerService.addScore(player);
                }
            }
    
            partitaService.terminaPartita(partita);
        }
    }

    public Partita getPartita() {
        return tpartita;
    }
}
    

