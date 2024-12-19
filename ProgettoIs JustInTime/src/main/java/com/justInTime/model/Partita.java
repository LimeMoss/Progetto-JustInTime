package com.justInTime.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Partita {

    // --- Dati principali della partita ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataInizio;
    
    @Transient
    private GameState gameState;  // Stato corrente del gioco

    private boolean segnalato;

    // --- Giocatori ---
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "partita_giocatori",
        joinColumns = @JoinColumn(name = "partita_id"),
        inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<Player> giocatori;

    @Transient
    private int indiceGiocatoreCorrente;  // Indice del giocatore corrente

    // --- Mazzi ---
    @Transient
    private Mazzo mazzoNormale;

    @Transient
    private Mazzo mazzoScarto;

    // --- Costruttore ---
    public Partita() {
        this.dataInizio = new Date();
        this.segnalato = false;
        this.gameState = new StartGameState();
        this.giocatori = new ArrayList<>();
        this.mazzoNormale = MazzoFactory.createMazzo("pesca");
        this.mazzoScarto = MazzoFactory.createMazzo("scarto");
        this.indiceGiocatoreCorrente = 0;
    }

    // --- Funzioni di gestione delle carte ---
    
    // Gioca una carta
    public void giocaCarta(Carta carta, int index) {
        if (cartaGiocabile(carta)) {
            if (carta.getValore() == 99) {
                carta.applicaEffetto(getGiocatoreCorrente());
            }
            List<Carta> carte = getGiocatoreCorrente().getMano();
            carte.remove(index);
            mazzoScarto.aggiungi(carta);
        } else {
            throw new RuntimeException("Impossibile Giocare Questa carta");
        }
    }

    // Verifica se la carta è giocabile
    public boolean cartaGiocabile(Carta carta) {
        int specialValue = 99;
        int value;
        if (mazzoScarto instanceof MazzoScarto) {
             value = ((MazzoScarto) mazzoScarto).ultimaCartaScartata().getValore();
             return carta.getValore() == value + 1 || carta.getValore() == value - 1 || carta.getValore() == specialValue;
        }
       throw new RuntimeException ("Il mazzo non è un mazzo di scarto");
}

    // --- Getter e Setter ---

    // Stato della partita
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    // Giocatori
    public List<Player> getGiocatori() {
        return giocatori;
    }

    public void setGiocatori(List<Player> giocatori) {
        this.giocatori = giocatori;
    }

    // Giocatore corrente
    public Player getGiocatoreCorrente() {
        return this.giocatori.get(getIndiceGiocatoreCorrente());
    }

    // Indice del giocatore corrente
    public int getIndiceGiocatoreCorrente() {
        return indiceGiocatoreCorrente;
    }

    public void setIndiceGiocatoreCorrente(int indiceGiocatoreCorrente) {
        this.indiceGiocatoreCorrente = indiceGiocatoreCorrente;
    }

    // Mazzi
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
        if (mazzoNormale instanceof MazzoScarto) {
            return (MazzoScarto) mazzoNormale;
        }
                return null;
    }

    public void setMazzoScarto(MazzoScarto mazzoScarto) {
        this.mazzoScarto = mazzoScarto;
    }

    // Altri getter e setter
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
