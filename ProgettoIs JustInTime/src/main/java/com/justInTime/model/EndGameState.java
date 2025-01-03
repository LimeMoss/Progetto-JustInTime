package com.justInTime.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.justInTime.service.PartitaService;

/**
 * Classe che rappresenta lo stato finale di una partita.
 * Implementa l'interfaccia {@link GameState} e gestisce le operazioni
 * da eseguire quando una partita termina.
 */
@Component
public class EndGameState implements GameState {

    @Autowired
    private PartitaService partitaService;

    /**
     * Esegue le operazioni necessarie al termine di una partita.
     * Utilizza {@link PartitaService}.
     *
     * @param partita La partita corrente da terminare.
     */
    @Override
    public void execute(Partita partita) {

            
            partitaService.terminaPartita(partita.getId());
    }
}
