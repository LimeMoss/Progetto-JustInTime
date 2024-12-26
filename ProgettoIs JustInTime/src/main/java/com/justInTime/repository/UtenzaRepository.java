package com.justInTime.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.justInTime.model.Utente;

@Repository
public interface UtenzaRepository extends JpaRepository<Utente, Long>{
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Utente> findByUsernameOrEmail(String username, String email);
}
