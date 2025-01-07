package com.justInTime.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.justInTime.model.Carta;
import com.justInTime.model.EndGameState;
import com.justInTime.model.GameState;
import com.justInTime.model.MazzoScarto;
import com.justInTime.model.Partita;
import com.justInTime.model.PauseState;
import com.justInTime.model.Player;

import com.justInTime.repository.PartitaRepository;

@Service
public class PartitaService {

    @Autowired
    private PartitaRepository partitaRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MazzoScartoService mazzoScartoService;

    @Autowired
    private MazzoPescaService mazzoPescaService;

    @Autowired
    @Lazy
    @Qualifier("startGameState")
    private GameState startGameState;

    @Autowired
    @Lazy
    @Qualifier("pauseState")
    private GameState pauseState;

    @Autowired
    @Lazy
    @Qualifier("endGameState")
    private GameState EndGameState;

    @Autowired
    @Lazy
    @Qualifier("turnState")
    private GameState turnState;

    @Async
    public void iniziaPartitaAsync(Long partitaId) {
        iniziaPartita(partitaId);
    }

    public void iniziaPartita(Long partitaId) {
        Partita partita = getPartita(partitaId);
        if (partita.getGiocatori().size() < 2) {
            throw new RuntimeException("Numero insufficiente di giocatori");
        }

        List<Player> giocatori = new ArrayList<>(partita.getGiocatori());

        for (Player player : giocatori) {
            playerService.addGame(player.getId());
        }

        setsGameState(partita.getId(), startGameState);
    }

    public Partita getPartita(Long partitaId) {
        return partitaRepository.findById(partitaId)
                .orElseThrow(() -> new RuntimeException("Partita non trovata"));
    }

    /**
     * Gioca una carta dalla mano del giocatore corrente e passa il turno
     * successivo.
     * 
     * @param partitaId  l'ID della partita
     * @param cartaIndex l'indice della carta nella mano del giocatore corrente
     *                   da giocare
     * @return la partita aggiornata
     * @throws RuntimeException se la carta non giocabile
     */
    public Partita giocaCarta(Long partitaId, int cartaIndex) {
        Partita partita = getPartita(partitaId);
        Player giocatoreCorrente = partita.getGiocatoreCorrente();
        Carta carta = giocatoreCorrente.getMano().get(cartaIndex);

        if (cartaGiocabile(partita, carta)) {
            if (carta.getValore() == 99) {
                carta.applicaEffetto(giocatoreCorrente);
            }
            playerService.rimuoviCartaDallaMano(giocatoreCorrente.getId(), cartaIndex);
            mazzoScartoService.aggiungiCarta(partita.getMazzoScarto(), carta);
            partita.getGiocatoreCorrente().setTurnoInPausa(true);
            setsGameState(partitaId, pauseState);
            return partita;
        }
        throw new RuntimeException("Carta non giocabile");
    }

    /**
     * Verifica se la carta puo essere giocata rispetto allo stato della partita.
     * La carta pu essere giocata se il suo valore maggiore o minore di 1 rispetto
     * al valore dell'ultima carta scartata o se una carta speciale (valore 99).
     * Se il mazzo scarto vuoto, la carta pu essere giocata in ogni caso.
     * 
     * @param partita la partita corrente
     * @param carta   la carta da verificare
     * @return true se la carta pu essere giocata, false altrimenti
     */
    private boolean cartaGiocabile(Partita partita, Carta carta) {
        int specialValue = 99;
        MazzoScarto mazzoScarto = partita.getMazzoScarto();
        Carta ultimaCarta;

        if (mazzoScarto != null && !mazzoScarto.isEmpty()) {
            ultimaCarta = mazzoScartoService.ultimaCartaScartata(partita.getMazzoScarto());
            if (ultimaCarta != null) {
                int value = ultimaCarta.getValore();
                return carta.getValore() == value + 1 ||
                        carta.getValore() == value - 1 ||
                        carta.getValore() == specialValue;
            }
        }

        return true;
    }

    /**
     * Distribuisce 5 carte iniziali ad ogni giocatore partecipante alla partita.
     * 
     * @param partita la partita corrente
     */
    public void distribuisciCarteIniziali(Long PartitaId) {
        Partita partita = getPartita(PartitaId);

        // Crea una copia della lista dei giocatori
        List<Player> giocatori = new ArrayList<>(partita.getGiocatori());

        for (Player giocatore : giocatori) {
            for (int i = 0; i < 5; i++) {
                // Pesca la carta dal mazzo e aggiungila alla mano del giocatore
                playerService.aggiungiCartaAllaMano(
                        giocatore.getId(),
                        mazzoPescaService.pescaCarta(partita.getMazzoNormale()));
            }
        }
    }

    /**
     * Termina la partita con l'id specificato.
     * Imposta lo stato della partita a "fine partita" e salva le modifiche.
     * 
     * @param partitaId l'id della partita da terminare
     */
    public void terminaPartita(Long partitaId) {
        Partita partita = getPartita(partitaId);
        List<Player> giocatori = new ArrayList<>(partita.getGiocatori()); // Crea una copia della lista
        for (Player giocatore : giocatori) {
            playerService.savePlayer(giocatore.getId());
        }
        partita.setFinita(true);
        partitaRepository.save(partita);
    }

    /**
     * Imposta lo stato della partita specificata.
     * Lo stato della partita viene settato al nuovo stato specificato.
     * 
     * @param partita   la partita su cui impostare lo stato
     * @param gamestate il nuovo stato della partita
     */
    public void setsGameState(Long partitaId, GameState gamestate) {
        Partita partita = getPartita(partitaId);
        partita.setGameState(gamestate);
        partita.getGameState().execute(partita);

    }

    public Partita pescaCarta(Long PartitaId) {
        Partita partita = getPartita(PartitaId);
        Player player = partita.getGiocatoreCorrente();
        playerService.aggiungiCartaAllaMano(player.getId(), mazzoPescaService.pescaCarta(partita.getMazzoNormale()));
        player.setTurnoInPausa(true);

        setsGameState(PartitaId, new PauseState());

        return partita;
    }

    public Partita playerReady(Long PartitaId) {
        Partita partita = getPartita(PartitaId);
        GameState gamestate = partita.getGameState();
        if (gamestate instanceof PauseState) {
            ((PauseState) gamestate).playerReady();
        }
        return partita;
    }

    public Partita tempoTerminato(Long partitaId) {
        Partita partita = getPartita(partitaId);

        setsGameStateArgument(partitaId, EndGameState, "TempoTerminato");

        return partita;
    }

    public void setsGameStateArgument(Long partitaId, GameState gameState, String Arg) {
        Partita partita = getPartita(partitaId);
        partita.setGameState(gameState);

        if (gameState instanceof EndGameState) {
            ((EndGameState) gameState).execute(partita, Arg);
        } else {
            partita.getGameState().execute(partita);
        }
    }

    public int getCurrentPlayerTimer(Long partitaId) {
        Partita partita = getPartita(partitaId);
        Player player = partita.getGiocatoreCorrente();
        return playerService.getTimer(player.getId());

    }

}