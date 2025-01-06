package com.justInTime.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.justInTime.model.Player;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAllByOrderByMaxScoreDesc();


    /**
     * Ritorna la lista di tutti i giocatori presenti nel sistema,
     * ordinati per paese di provenienza e punteggio massimo.
     * 
     * @return la lista di tutti i giocatori
     */
    @Query("SELECT p FROM Player p JOIN p.utente u ORDER BY p.maxScore DESC")
    List<Player> findAllPlayersOrderByCountryAndMaxScore();
    
    @Query("SELECT p FROM Player p WHERE p.utente.id = :id")
Optional<Player> findPlayerByIdCustom(@Param("id") Long id);


}
