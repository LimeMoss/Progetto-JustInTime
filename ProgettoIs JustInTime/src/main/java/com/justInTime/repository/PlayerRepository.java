package com.justInTime.repository;

import com.justInTime.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByName(String name); //optional significa che il risultato della ricerca può essere null
    List<Player> findAllByOrderByMaxScoreDesc();

}
