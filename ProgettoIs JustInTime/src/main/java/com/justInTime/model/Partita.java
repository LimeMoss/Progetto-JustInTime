package com.justInTime.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;

@Entity
public class Partita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataInizio;
    
    @Transient
    private GameState gameState;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
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

    @Transient
    Map<Player, List<Achievements>> playerAchievements = new HashMap<>();


    public Partita() {
        this.dataInizio = new Date();
        this.giocatori = new ArrayList<>();
        this.mazzoNormale = MazzoFactory.createMazzo("pesca");
        this.mazzoScarto = MazzoFactory.createMazzo("scarto");
        this.indiceGiocatoreCorrente = 0;
    }


    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        gameState.execute(this);;
    }

    public void setGameStateArgument(EndGameState gameState, String Arg) {
        this.gameState = gameState;
         gameState.execute(this, Arg);;
    }

    public List<Player> getGiocatori() {
        return this.giocatori;
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

    public void setMazzoScarto(Mazzo scarto) {
        this.mazzoScarto = scarto;
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

    public Map<Player, List<Achievements>> getPlayerAchievements(){
        return this.playerAchievements;
    }

}