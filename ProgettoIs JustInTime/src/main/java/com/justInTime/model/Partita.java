package com.justInTime.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Rappresenta una partita di gioco.
 * La classe gestisce lo stato del gioco, i giocatori, i mazzi e altre informazioni rilevanti per il gioco.
 */
@Entity
public class Partita {

    /**
     * Identificativo univoco della partita.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Data e ora di inizio della partita.
     */
    private Date dataInizio;
    
    /**
     * Stato attuale del gioco.
     */
    @Transient
    private GameState gameState;

    /**
     * Indica se la partita è stata segnalata.
     */
    private boolean segnalato;

    /**
     * Lista di giocatori coinvolti nella partita.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "partita_giocatori",
        joinColumns = @JoinColumn(name = "partita_id"),
        inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<Player> giocatori;

    /**
     * Indice del giocatore corrente nella partita.
     */
    @Transient
    private int indiceGiocatoreCorrente;

    /**
     * Mazzo normale per la partita.
     */
    @Transient
    private Mazzo mazzoNormale;

    /**
     * Mazzo degli scarti per la partita.
     */
    @Transient
    private Mazzo mazzoScarto;

    /**
     * Costruttore della classe. Inizializza i valori di base della partita.
     */
    public Partita() {
        this.dataInizio = new Date();
        this.segnalato = false;
        this.gameState = new StartGameState();
        this.giocatori = new ArrayList<>();
        this.mazzoNormale = MazzoFactory.createMazzo("pesca");
        this.mazzoScarto = MazzoFactory.createMazzo("scarto");
        this.indiceGiocatoreCorrente = 0;
    }

    /**
     * Restituisce lo stato attuale del gioco.
     *
     * @return lo stato del gioco.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Imposta lo stato del gioco.
     *
     * @param gameState lo stato del gioco.
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Restituisce la lista dei giocatori della partita.
     *
     * @return la lista dei giocatori.
     */
    public List<Player> getGiocatori() {
        return giocatori;
    }

    /**
     * Imposta la lista dei giocatori della partita.
     *
     * @param giocatori la lista dei giocatori.
     */
    public void setGiocatori(List<Player> giocatori) {
        this.giocatori = giocatori;
    }

    /**
     * Restituisce il giocatore corrente.
     *
     * @return il giocatore corrente.
     */
    public Player getGiocatoreCorrente() {
        return this.giocatori.get(getIndiceGiocatoreCorrente());
    }

    /**
     * Restituisce l'indice del giocatore corrente.
     *
     * @return l'indice del giocatore corrente.
     */
    public int getIndiceGiocatoreCorrente() {
        return indiceGiocatoreCorrente;
    }

    /**
     * Imposta l'indice del giocatore corrente.
     *
     * @param indiceGiocatoreCorrente l'indice del giocatore corrente.
     */
    public void setIndiceGiocatoreCorrente(int indiceGiocatoreCorrente) {
        this.indiceGiocatoreCorrente = indiceGiocatoreCorrente;
    }

    /**
     * Restituisce il mazzo normale della partita.
     *
     * @return il mazzo normale della partita.
     */
    public MazzoPesca getMazzoNormale() {
        if (mazzoNormale instanceof MazzoPesca) {
            return (MazzoPesca) mazzoNormale;
        }
        return null;
    }

    /**
     * Imposta il mazzo normale della partita.
     *
     * @param mazzoNormale il mazzo normale della partita.
     */
    public void setMazzoNormale(Mazzo mazzoNormale) {
        this.mazzoNormale = mazzoNormale;
    }

    /**
     * Restituisce il mazzo degli scarti della partita.
     *
     * @return il mazzo degli scarti della partita.
     */
    public MazzoScarto getMazzoScarto() {
        if (mazzoScarto instanceof MazzoScarto) {
            return (MazzoScarto) mazzoScarto;
        }
        return null;
    }

    /**
     * Imposta il mazzo degli scarti della partita.
     *
     * @param mazzoScarto il mazzo degli scarti della partita.
     */
    public void setMazzoScarto(MazzoScarto mazzoScarto) {
        this.mazzoScarto = mazzoScarto;
    }

    /**
     * Restituisce l'identificativo univoco della partita.
     *
     * @return l'identificativo della partita.
     */
    public Long getId() {
        return id;
    }

    /**
     * Imposta l'identificativo univoco della partita.
     *
     * @param id l'identificativo della partita.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Restituisce la data e ora di inizio della partita.
     *
     * @return la data di inizio della partita.
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * Imposta la data e ora di inizio della partita.
     *
     * @param dataInizio la data di inizio della partita.
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * Indica se la partita è stata segnalata.
     *
     * @return true se la partita è segnalata, false altrimenti.
     */
    public boolean isSegnalato() {
        return segnalato;
    }

    /**
     * Imposta lo stato di segnalazione della partita.
     *
     * @param segnalato true se la partita deve essere segnalata, false altrimenti.
     */
    public void setSegnalato(boolean segnalato) {
        this.segnalato = segnalato;
    }
}
