package com.justInTime.repository;

import com.justInTime.model.Partita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartitaRepository extends JpaRepository<Partita, Long> {
    
}
