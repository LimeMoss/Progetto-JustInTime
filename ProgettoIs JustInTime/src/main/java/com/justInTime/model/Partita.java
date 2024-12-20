package com.justInTime.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Partita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataInizio;
    
    @Transient
    private GameState gameState;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "partita_giocatori",
        joinColumns = @JoinColumn(name = "partita_id"),
        inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<Player> giocatori;

    @Transient
    private int indiceGiocatoreCorrente;

    @Transient
    private Mazzo mazzoNormale;

    @Transient
    private Mazzo mazzoScarto;

    public Partita() {
        this.dataInizio = new Date();
        this.segnalato = false;
        this.gameState = new StartGameState();
        this.giocatori = new ArrayList<>();
        this.mazzoNormale = MazzoFactory.createMazzo("pesca");
        this.mazzoScarto = MazzoFactory.createMazzo("scarto");
        this.indiceGiocatoreCorrente = 0;
    }

    // Getters and Setters
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<Player> getGiocatori() {
        return giocatori;
    }

    public void setGiocatori(List<Player> giocatori) {
        this.giocatori = giocatori;
    }

    public Player getGiocatoreCorrente() {
        return this.giocatori.get(getIndiceGiocatoreCorrente());
    }

    public int getIndiceGiocatoreCorrente() {
        return indiceGiocatoreCorrente;
    }

    public void setIndiceGiocatoreCorrente(int indiceGiocatoreCorrente) {
        this.indiceGiocatoreCorrente = indiceGiocatoreCorrente;
    }

    public MazzoPesca getMazzoNormale() {
        if (mazzoNormale instanceof MazzoPesca) {
            return (MazzoPesca) mazzoNormale;
        }
        return null;
    }

    public void setMazzoNormale(Mazzo mazzoNormale) {
        this.mazzoNormale = mazzoNormale;
    }

    public MazzoScarto getMazzoScarto() {
        if (mazzoScarto instanceof MazzoScarto) {
            return (MazzoScarto) mazzoScarto;
        }
        return null;
    }

    public void setMazzoScarto(MazzoScarto mazzoScarto) {
        this.mazzoScarto = mazzoScarto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public boolean isSegnalato() {
        return segnalato;
    }

    public void setSegnalato(boolean segnalato) {
        this.segnalato = segnalato;
    }
}