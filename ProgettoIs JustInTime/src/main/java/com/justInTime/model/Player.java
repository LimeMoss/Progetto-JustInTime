package com.justInTime.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class Player implements UtenzaBridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private int durataTurno;

    @Transient
    private boolean turnoInPausa;

    @Transient
    private List<Carta> mano;

    private String name;

    @ManyToMany(mappedBy = "giocatori", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Partita> partite = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "utenza_id")
    private UtenzaBridge utenzaBridge;

    private int maxScore;
    private String paese; // Aggiungi il campo "paese" in Player

    // Costruttori
    public Player() {
        mano = new ArrayList<Carta>();
    }

    public Player(String name, int maxScore) {
        this.name = name;
        this.maxScore = maxScore;
        mano = new ArrayList<Carta>();
    }

    // Getter e Setter
    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    // Metodo per associare un Player con un'utenza
    public void associaUtenza(Utenza utenza) {
        this.utenzaBridge = utenza; // Associa l'utenza al player
        if (utenza != null) {
            this.paese = utenza.getPaese(); // Imposta il paese del player uguale a quello dell'utenza
        }
    }

    // Metodi Utility
    public void aggiungiCartaAllaMano(Carta carta) {
        this.mano.add(carta);
    }

    public boolean rimuoviCartaDallaMano(Carta carta) {
        return this.mano.remove(carta);
    }

    public void increaseMaxScore() {
        this.maxScore++;
    }

    // Metodi Override (UtenzaBridge)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
