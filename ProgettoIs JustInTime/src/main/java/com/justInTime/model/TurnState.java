package com.justInTime.model;

import org.springframework.stereotype.Component;

import com.justInTime.service.PartitaService;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class TurnState implements GameState {
    
    @Autowired
    private PartitaService partitaService;

    @Override
    public void execute(Partita partita) {
        Player giocatoreCorrente = partita.getGiocatori().get(partita.getIndiceGiocatoreCorrente());
        System.out.println("Turno di " + giocatoreCorrente.getName());

        int tempoRestante = giocatoreCorrente.getDurataTurno();

        while (tempoRestante > 0) {
            if (giocatoreCorrente.isTurnoInPausa()) {
                try {
                    Thread.sleep(1000);  // Pausa per 1 secondo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            // Riduce il tempo
            tempoRestante--;
            giocatoreCorrente.setDurataTurno(tempoRestante);
            System.out.println("Tempo rimasto per " + giocatoreCorrente.getName() + ": " + tempoRestante + " secondi.");

            try {
                Thread.sleep(1000); // Simula il passare del tempo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        partitaService.passaAlProssimoGiocatore(partita);  // Delegato al servizio

        // Controllo se la partita è finita
        if (partita.getGameState() instanceof EndGameState) {
            System.out.println("La partita è terminata!");
            return;  // Termina l'esecuzione se il gioco è finito
        }

        System.out.println("Passando al prossimo giocatore...");
    }
}