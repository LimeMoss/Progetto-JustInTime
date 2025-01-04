package com.justInTime.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.justInTime.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAllByOrderByMaxScoreDesc();
    List<Player> findAllByNome(String nome);
    /**
     * Ritorna la lista di tutti i giocatori presenti nel sistema,
     * ordinati per paese di provenienza e punteggio massimo.
     * 
     * @return la lista di tutti i giocatori
     */
    @Query("SELECT p FROM Player p JOIN p.utente u ORDER BY p.maxScore DESC")
    List<Player> findAllPlayersOrderByCountryAndMaxScore();


}
