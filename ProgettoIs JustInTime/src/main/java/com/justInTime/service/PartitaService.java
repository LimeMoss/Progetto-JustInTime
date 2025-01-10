package com.justInTime.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.justInTime.model.StartGameState;
import com.justInTime.repository.PartitaRepository;


@Service
public class PartitaService {
    private static final Logger logger = LoggerFactory.getLogger(PartitaService.class);

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


    

    @Async
    public void iniziaPartitaAsync(Partita partita) {
        iniziaPartita(partita);

    }

    public void iniziaPartita(Partita partita) {
        if (partita.getGiocatori().size() < 2) {
            throw new RuntimeException("Numero insufficiente di giocatori");
        }

        List<Player> giocatori = new ArrayList<>(partita.getGiocatori());

        for (Player player : giocatori) {
            playerService.addGame(player.getId());
        }

   
        startGameState.execute(partita);

    }

    /**
     * Ritorna la partita con l'ID specificato.
     * 
     * @param partitaId l'ID della partita
     * @return la partita con l'ID specificato
     * @throws RuntimeException se la partita non esiste
     */
    
    public Partita getPartitaRepository(Long partitaId) {
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

   
    public Carta giocaCarta(Partita partita, int cartaIndex) {
        logger.info("Inizio esecuzione di giocaCarta per la partita ID: {}, cartaIndex: {}", partita.getId(), cartaIndex);

        Player giocatoreCorrente = partita.getGiocatoreCorrente();
        if (giocatoreCorrente == null) {
            logger.error("Giocatore corrente è null nella partita ID: {}", partita.getId());
            throw new IllegalStateException("Giocatore corrente non può essere null");
        }
        logger.info("Giocatore corrente: {} con ID: {}", giocatoreCorrente.getUtente().getName(), giocatoreCorrente.getId());

        Carta carta;
        try {
            carta = giocatoreCorrente.getMano().get(cartaIndex);
        } catch (IndexOutOfBoundsException e) {
            logger.error("Indice della carta non valido: {}. Mano del giocatore contiene {} carte.", cartaIndex, giocatoreCorrente.getMano().size());
            throw new IllegalArgumentException("Indice carta non valido", e);
        }

        logger.info("Carta selezionata: {} (tipo: {}, valore: {})", carta.getTipo(), carta.getTipo(), carta.getValore());

        if (cartaGiocabile(partita, carta)) {
            logger.info("La carta {} è giocabile.", carta.getTipo());

            if (carta.getTipo().equals("Rallenta")) {
                logger.info("Applicazione effetto 'Rallenta' al giocatore: {}", giocatoreCorrente.getUtente().getName());
                carta.applicaEffetto(giocatoreCorrente);
            } else if (carta.getTipo().equals("Accelera")) {
                List<Player> giocatori = partita.getGiocatori();
                int indiceSuccessivo = (partita.getIndiceGiocatoreCorrente() + 1) % giocatori.size();
                logger.info("Applicazione effetto 'Accelera' al giocatore successivo: {} (indice: {})", giocatori.get(indiceSuccessivo).getUtente().getName(), indiceSuccessivo);
                carta.applicaEffetto(giocatori.get(indiceSuccessivo));
            }

            playerService.rimuoviCartaDallaMano(giocatoreCorrente, cartaIndex);
            logger.info("Carta {} rimossa dalla mano del giocatore {}", carta.getTipo(), giocatoreCorrente.getUtente().getName());

            mazzoScartoService.aggiungiCarta(partita.getMazzoScarto(), carta);
            logger.info("Carta {} aggiunta al mazzo di scarto della partita ID: {}", carta.getTipo(), partita.getId());

            giocatoreCorrente.setTurnoInPausa(true);
            logger.info("Il turno del giocatore {} è stato messo in pausa", giocatoreCorrente.getUtente().getName());
            
        

            return carta;
        } else {
            logger.warn("La carta {} non è giocabile (tipo: {}, valore: {}).", carta.getTipo(), carta.getTipo(), carta.getValore());
            throw new RuntimeException("Carta non giocabile");
        }
    }

    @Async
public void eseguiPauseStateAsync(Partita partita) {
    pauseState.execute(partita);
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
        final int specialValue = 99;
        MazzoScarto mazzoScarto = partita.getMazzoScarto();
    
        if (mazzoScarto == null || mazzoScarto.isEmpty()) {
            // Mazzo vuoto, considera la carta giocabile
            logger.info("Mazzo di scarto vuoto o non inizializzato, carta giocabile di default: {}", carta.getTipo());
            return true;
        }
    
        // Recupera l'ultima carta scartata
        Carta ultimaCarta = mazzoScartoService.ultimaCartaScartata(mazzoScarto);
        if (ultimaCarta == null) {
            logger.warn("Nessuna ultima carta scartata trovata, carta giocabile di default: {}", carta.getTipo());
            return true;
        }
    
        // Verifica le condizioni di giocabilità
        int ultimaValore = ultimaCarta.getValore();
        boolean giocabile = (carta.getValore() == ultimaValore + 1 ||
                            carta.getValore() == ultimaValore - 1 ||
                            carta.getValore() == specialValue||
                            ultimaValore==specialValue);
    
        if (giocabile) {
            logger.info("Carta giocabile: {} (valore: {}) rispetto all'ultima carta scartata: {} (valore: {})",
                        carta.getTipo(), carta.getValore(), ultimaCarta.getTipo(), ultimaValore);
        } else {
            logger.info("Carta NON giocabile: {} (valore: {}) rispetto all'ultima carta scartata: {} (valore: {})",
                        carta.getTipo(), carta.getValore(), ultimaCarta.getTipo(), ultimaValore);


        }
        
        partita.getGiocatoreCorrente().setTurnoInPausa(true);

        return giocabile;
    }
    
    /**
     * Distribuisce 5 carte iniziali ad ogni giocatore partecipante alla partita.
     * 
     * @param partita la partita corrente
     */
    public void distribuisciCarteIniziali(Partita partita) {
      

        List<Player> giocatori = new ArrayList<>(partita.getGiocatori());

        for (Player giocatore : giocatori) {
            for (int i = 0; i < 5; i++) {

                playerService.aggiungiCartaAllaMano(
                        giocatore,
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
    public List<Player> terminaPartita(Partita partita) {

        List <Player> podio= new ArrayList<>();
        List<Player> giocatori = new ArrayList<>(partita.getGiocatori());
        giocatori.sort((p1, p2) -> Double.compare(playerService.estimateScore(p2), playerService.estimateScore(p1)));
        podio.addAll(giocatori);


      
        for (Player giocatore : giocatori) {
            
            giocatore.getPartite().remove(partita);
            playerService.savePlayer(giocatore.getId());
        }

        partita.setFinita(true);
        partitaRepository.save(partita);
        partita.setGameState(null);
        return podio;

      
    }

    /**
     * Imposta lo stato della partita specificata.
     * Lo stato della partita viene settato al nuovo stato specificato.
     * 
     * @param partita   la partita su cui impostare lo stato
     * @param gamestate il nuovo stato della partita
     */
  
    public void setsGameState(Partita partita, GameState gamestate) {
       
        partita.setGameState(gamestate);
      

    }

    public Carta pescaCarta(Partita partita) {


        Player player = partita.getGiocatoreCorrente();
   


        Carta carta = playerService.aggiungiCartaAllaMano(player,
        mazzoPescaService.pescaCarta(partita.getMazzoNormale()));
        playerService.riduzioneTurnoPlayer(player);



        player.setTurnoInPausa(true);
   

        return carta;
    }

   public Partita nextplayerReady(Partita partita) {
    GameState gamestate = partita.getGameState();
    if (gamestate instanceof PauseState thePauseState) {
        thePauseState.playerReady();
    }
    return partita;
}


    public Partita playerReady(Partita partita) {
      
        
        if (partita == null) {
            throw new IllegalArgumentException("Partita non trovata.");
        }

       
        GameState gameState = partita.getGameState();
       


     
        if (gameState instanceof StartGameState startGameState1) {
   
            startGameState1.playerReady();
        } else {
          
            throw new IllegalStateException("Non è possibile segnare il giocatore come pronto in questo stato del gioco.");
        }
    
        return partita;
    }
    

    public Partita tempoTerminato(Partita partita) {

        setsGameStateArgument(partita, EndGameState, "TempoTerminato");

        return partita;
    }

    public void setsGameStateArgument(Partita partita, GameState gameState, String Arg) {
    
        partita.setGameState(gameState);

        if (gameState instanceof EndGameState endGameState) {
            endGameState.execute(partita, Arg);
        } else {
            partita.getGameState().execute(partita);
        }
    }

    public int getCurrentPlayerTimer(Partita partita) {

        Player player = partita.getGiocatoreCorrente();
        return playerService.getTimer(player);

    }

    public boolean isFinished(Partita partita) {
        return partita.isFinita();

    }

    public List<Carta> getGiocatoreCorrenteMano(Partita partita) {
        Player player = partita.getGiocatoreCorrente();
        return playerService.getPlayerMano(player);

    }

    public Carta getLastCardScarto(Partita partita) {
        MazzoScarto scarto = partita.getMazzoScarto();
        return mazzoScartoService.ultimaCartaScartata(scarto);

    }

    public int estimatePlayerScore(Player player){
       return playerService.estimateScore(player);
    }

}