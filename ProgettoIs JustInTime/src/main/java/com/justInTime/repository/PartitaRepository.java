package com.justInTime.repository;

import com.justInTime.model.Partita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartitaRepository extends JpaRepository<Partita, Long> {


    // Trova una partita in base alla sua data di inizio /
    Optional<Partita> findByDataInizio(java.util.Date dataInizio);

    // Trova tutte le partite segnalate, ordinate per data di inizio
    List<Partita> findAllBySegnalatoTrueOrderByDataInizioDesc();

    // Trova tutte le partite in cui un giocatore è coinvolto, ordinato per data di inizio
    List<Partita> findAllByGiocatori_Id(Long playerId);
}
