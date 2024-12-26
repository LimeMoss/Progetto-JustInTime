package com.justInTime.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justInTime.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAllByOrderByMaxScoreDesc();

}
