package com.justInTime.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.justInTime.DTO.FullPlayerDataDTO;
import com.justInTime.model.Carta;
import com.justInTime.model.EndGameState;
import com.justInTime.model.GameState;
import com.justInTime.model.MazzoScarto;
import com.justInTime.model.Partita;

import com.justInTime.model.Player;
import com.justInTime.model.StartGameState;
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

    private Map<Long, Partita> partiteInCorso = new HashMap<>();


    public Partita getPartita(Long partitaId) {
        return partiteInCorso.get(partitaId);
    }

    public void addPartita(Long partitaId, Partita partita) {
        partiteInCorso.put(partitaId, partita);
    }

    public void updatePartita(Long partitaId, Partita partita) {
        partiteInCorso.put(partitaId, partita);
    }

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


        Player giocatoreCorrente = partita.getGiocatoreCorrente();
        if (giocatoreCorrente == null) {
          
            throw new IllegalStateException("Giocatore corrente non può essere null");
        }


        Carta carta;
        try {
            carta = giocatoreCorrente.getMano().get(cartaIndex);
        } catch (IndexOutOfBoundsException e) {
        
            throw new IllegalArgumentException("Indice carta non valido", e);
        }

 

        if (cartaGiocabile(partita, carta)) {
    

            if (carta.getTipo().equals("Rallenta")) {
             
                carta.applicaEffetto(giocatoreCorrente);
            } else if (carta.getTipo().equals("Accelera")) {
                List<Player> giocatori = partita.getGiocatori();
                int indiceSuccessivo = (partita.getIndiceGiocatoreCorrente() + 1) % giocatori.size();
       
                carta.applicaEffetto(giocatori.get(indiceSuccessivo));
            }

            playerService.rimuoviCartaDallaMano(giocatoreCorrente, cartaIndex);
  

            mazzoScartoService.aggiungiCarta(partita.getMazzoScarto(), carta);
     

            giocatoreCorrente.setTurnoInPausa(true);
         
            
    


            return carta;
        } else {
        
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
       
            return true;
        }

        Carta ultimaCarta = mazzoScartoService.ultimaCartaScartata(mazzoScarto);
        if (ultimaCarta == null) {
      
            return true;
        }

          if (ultimaCarta.getValore() == specialValue) {
            return true;
          }

        int ultimaValore = ultimaCarta.getValore();
        boolean giocabile = (carta.getValore() == ultimaValore + 1 ||
                            carta.getValore() == ultimaValore - 1 ||
                            carta.getValore() == specialValue||
                            ultimaValore==specialValue);
    

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
    public List<FullPlayerDataDTO> terminaPartita(Partita partita) {

        List <FullPlayerDataDTO> podio= new ArrayList<>();
        List<Player> giocatori = new ArrayList<>(partita.getGiocatori());
        giocatori.sort((p1, p2) -> Double.compare(playerService.estimateScore(p2), playerService.estimateScore(p1)));
       ;


      
        for (Player giocatore : giocatori) {
            podio.add(new FullPlayerDataDTO(giocatore.getUtente().getUsername()));
            giocatore.getPartite().remove(partita);
            playerService.savePlayer(giocatore.getId());
        }

        partita.setFinita(true);
        partitaRepository.save(partita);
        System.out.println("la partita è finita");
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




    public Partita playerReady(Long partitaId) {
       Partita partita = getPartita(partitaId);
        
        if (partita == null) {
            throw new IllegalArgumentException("Partita non trovata.");
        }

       
        GameState gameState = partita.getGameState();
       


     
        if (gameState instanceof StartGameState startGameState1) {
   
            startGameState1.playerReady(partitaId);
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