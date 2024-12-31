package com.justInTime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.justInTime.model.Carta;
import com.justInTime.model.EndGameState;
import com.justInTime.model.GameState;
import com.justInTime.model.MazzoScarto;
import com.justInTime.model.Partita;
import com.justInTime.model.PauseState;
import com.justInTime.model.Player;
import com.justInTime.model.StartGameState;
import com.justInTime.repository.PartitaRepository;


@Service
public class PartitaService {

    @Autowired
    private PartitaRepository partitaRepository;


    @Autowired
    private PlayerService playerService;

    public void iniziaPartita(Partita partita) {

        if (partita.getGiocatori().size() < 2) {
            throw new RuntimeException("Numero insufficiente di giocatori");
        }
        partita.setGameState(new StartGameState());
    }
    
    public Partita getPartita(Long partitaId) {
        return partitaRepository.findById(partitaId)
            .orElseThrow(() -> new RuntimeException("Partita non trovata"));
    }


    /**
     * Gioca la carta selezionata dal giocatore corrente.
     * La carta viene rimossa dalla mano del giocatore e aggiunta al mazzo scarto.
     * Se la carta ha un effetto speciale, viene applicato.
     * Lo stato della partita viene settato in pausa.
     * @param partitaId l'id della partita corrente
     * @param cartaIndex l'indice della carta da giocare
     * @return la partita aggiornata
     */
    public Partita giocaCarta(Long partitaId, int cartaIndex) {
        Partita partita = getPartita(partitaId);
        Player giocatoreCorrente = partita.getGiocatoreCorrente();
        Carta carta = giocatoreCorrente.getMano().get(cartaIndex);
        
        if (cartaGiocabile(partita, carta)) {
            if (carta.getValore() == 99) {
                carta.applicaEffetto(giocatoreCorrente);
            }
            giocatoreCorrente.getMano().remove(cartaIndex);
            partita.getMazzoScarto().aggiungi(carta);;
            partita.getGiocatoreCorrente().setTurnoInPausa(true);;
            partita.setGameState(new PauseState());
            return partitaRepository.save(partita);
        }
        throw new RuntimeException("Carta non giocabile");
    }

    /**
     * Verifica se la carta puo essere giocata rispetto allo stato della partita.
     * La carta pu  essere giocata se il suo valore  maggiore o minore di 1 rispetto
     * al valore dell'ultima carta scartata o se  una carta speciale (valore 99).
     * Se il mazzo scarto  vuoto, la carta pu  essere giocata in ogni caso.
     * @param partita la partita corrente
     * @param carta la carta da verificare
     * @return true se la carta pu  essere giocata, false altrimenti
     */
    private boolean cartaGiocabile(Partita partita, Carta carta) {
        int specialValue = 99;
        MazzoScarto mazzoScarto = partita.getMazzoScarto();
        
        if (mazzoScarto != null && !mazzoScarto.isEmpty()) {
       
            int value = mazzoScarto.ultimaCartaScartata().getValore();
            return carta.getValore() == value + 1 || 
                   carta.getValore() == value - 1 || 
                   carta.getValore() == specialValue;
        }
        
        return true;
    }

    /**
     * Distribuisce 5 carte iniziali ad ogni giocatore partecipante alla partita.
     * @param partita la partita corrente
     */
    public void distribuisciCarteIniziali(Partita partita) {
        for (Player giocatore : partita.getGiocatori()) {
            for (int i = 0; i < 5; i++) {
                playerService.aggiungiCartaAllaMano(giocatore.getId(),partita.getMazzoNormale().pescaCarta());
            }
        }
    }


    /**
     * Passa al prossimo giocatore nella partita.
     * Il giocatore corrente viene impostato al successivo nella lista dei giocatori della partita,
     * se il giocatore corrente  l'ultimo della lista, il prossimo giocatore sar  il primo della lista.
     * @param partita la partita corrente
     */


    /**
     * Termina la partita con l'id specificato.
     * Imposta lo stato della partita a "fine partita" e salva le modifiche.
     * @param partitaId l'id della partita da terminare
     */
    public void terminaPartita(Long partitaId) {
        Partita partita = getPartita(partitaId);
        partita.setGameState(new EndGameState());
        partitaRepository.save(partita);
    }

        /**
         * Imposta lo stato della partita specificata.
         * Lo stato della partita viene settato al nuovo stato specificato.
         * @param partita la partita su cui impostare lo stato
         * @param gamestate il nuovo stato della partita
         */
    public void setGameState(Partita partita, GameState gamestate){

            partita.setGameState(gamestate);

    }

    //TODO//
    //pescacarta per la partita//
        
}