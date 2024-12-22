package com.justInTime.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justInTime.model.Utenza;

@Repository
public interface UtenzaRepository extends JpaRepository<Utenza, Long>{
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Utenza> findByUsernameOrEmail(String username, String email);
     List<Utenza> findAllByOrderByMaxScoreDesc();
}
