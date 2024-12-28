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
     * Determina il vincitore e termina la partita utilizzando il servizio {@link PartitaService}.
     *
     * @param partita La partita corrente da terminare.
     */
    @Override
    public void execute(Partita partita) {
        // Determina il vincitore
        Player vincitore = determinaVincitore(partita);

        if (vincitore != null) {
            partitaService.terminaPartita(partita.getId());
        }
    }

    /**
     * Determina il vincitore della partita.
     * La logica attuale considera vincitore il primo giocatore che ha terminato le carte.
     *
     * @param partita La partita corrente.
     * @return Il giocatore vincitore, o {@code null} se nessun vincitore è determinato.
     */
    private Player determinaVincitore(Partita partita) {
        for (Player player : partita.getGiocatori()) {
            if (player.getMano().isEmpty()) {
                return player;
            }
        }
        return null;
    }
}
