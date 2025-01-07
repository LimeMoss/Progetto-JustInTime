package com.justInTime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.justInTime.model.*;
import com.justInTime.repository.*;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private UtenzaPlayerService utenzaPlayerService;
    


    /**
     * Crea un nuovo giocatore associato ad una specifica utenza.
     * 
     * @param name il nome del giocatore
     * @param utenzaId l'ID dell'utenza da associare
     * @return il giocatore creato
     * @throws RuntimeException se l'utenza specificata non esiste
     */
    @Transactional
    public Player creaGiocatore(Long utenzaId) {
        Utente utenza= utenzaPlayerService.trovaUtente(utenzaId);
        Player player = new Player();
        player.setUtente(utenza);  
        utenza.setPlayer(player);
        return playerRepository.save(player);
    }

    /**
     * Trova un giocatore dato il suo ID.
     *
     * @param playerId l'ID del giocatore da cercare
     * @return l'oggetto Player corrispondente all'ID fornito
     * @throws RuntimeException se il giocatore non esiste
     */

    public Player trovaGiocatore(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Giocatore non trovato."));
    }

    /**
     * Restituisce la lista di tutti i giocatori presenti nel sistema.
     * 
     * @return la lista di tutti i giocatori
     */
    public List<Player> trovaTuttiGiocatori() {
        return playerRepository.findAll();
    }


    /**
     * Aggiunge una carta alla mano di un giocatore.
     * L'operazione viene eseguita in modo atomico.
     * @param playerId l'ID del giocatore a cui aggiungere la carta
     * @param carta la carta da aggiungere
     * @return il giocatore con la carta aggiunta
     */
    @Transactional
    public Player aggiungiCartaAllaMano(Long playerId, Carta carta) {
        Player player = trovaGiocatore(playerId);
        player.getMano().add(carta);
        return playerRepository.save(player);
    }

/**
 * Rimuove una carta dalla mano di un giocatore.
 * L'operazione viene eseguita in modo atomico.
 * 
 * @param playerId l'ID del giocatore da cui rimuovere la carta
 * @param carta la carta da rimuovere
 * @return il giocatore con la carta rimossa
 */

    @Transactional
    public Player rimuoviCartaDallaMano(Long playerId, int cartaIndex) {
        Player player = trovaGiocatore(playerId);
        player.getMano().remove(cartaIndex); 
        return playerRepository.save(player);
    }

    /**
     * Incrementa il punteggio massimo del giocatore con l'ID fornito.
     * L'operazione viene eseguita in modo atomico.
     * 
     * @param playerId l'ID del giocatore a cui incrementare il punteggio
     * @return il giocatore con il punteggio massimo incrementato
     */
    @Transactional
    public Player incrementaMaxScore(Long playerId) {
        Player player = trovaGiocatore(playerId);
        player.increaseMaxScore();
        return playerRepository.save(player);
    }

    

/**
 * Elimina un giocatore dal repository in base al suo ID.
 * 
 * @param id l'ID del giocatore da eliminare
 */

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }


    /**
     * Ritorna la lista delle partite in cui un giocatore  coinvolto.
     * L'operazione viene eseguita in modo atomico.
     * @param playerId l'ID del giocatore a cui reperire le partite
     * @return la lista delle partite del giocatore
     */
    @Transactional
    public List<Partita> getPartiteGiocatore(Long playerId) {
        Player player = trovaGiocatore(playerId);
        return player.getPartite();
    }

    @Transactional
    public Player addVictory(Long playerId){
        
        Player player = trovaGiocatore(playerId);
        player.setVittorie(player.getVittorie()+1);
        player.setMaxScore(player.getMaxScore()+20);
        return playerRepository.save(player);


    }

    @Transactional
    public Player addGame(Long playerId){
        Player player = trovaGiocatore(playerId);
        player.setPartiteGiocate(player.getPartiteGiocate()+1);
       return playerRepository.save(player);
    
    }

    @Transactional
    public Player addScore(Long playerId){
        Player player= trovaGiocatore(playerId);
        player.setMaxScore(player.getMaxScore()+(10-(player.getMano().size())));
        return playerRepository.save(player);
    }
        @Transactional
    public Player savePlayer(Long playerId){
        Player player= trovaGiocatore(playerId);
        return playerRepository.save(player);
    }

    @Transactional
    public int getTimer(Long playerId){
        Player player= trovaGiocatore(playerId);
        return player.getDurataTurno();
    }

    public List<Carta> getPlayerMano(Long playerId){
        Player player= trovaGiocatore(playerId);
        return player.getMano();

    }


}