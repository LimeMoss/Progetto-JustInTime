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
public class Player implements abstractPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private int durataTurno;

    @Transient
    private boolean turnoInPausa;

    @Transient
    private List<Carta> mano;

    @Transient
    private String nome;




    @ManyToMany(mappedBy = "giocatori", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Partita> partite = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Utente_id")
    private Utente utente;


    private int maxScore;

    // Costruttori
    public Player() {
        mano = new ArrayList<Carta>();
    }

    public Player(String name, int maxScore) {
        this.nome=name;
        this.maxScore = maxScore;
        mano = new ArrayList<Carta>();
    }

    

    // Metodo per associare un Player con un'Utente
    public void associaUtente(Utente utente) {
        this.utente=utente;
     
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

    // Metodi Override (UtenteBridge)
    @Override
    public Long getId() {
        return id;
    }


    @Override
    public int getDurataTurno() {
        return this.durataTurno;
    }
    @Override
    public void setDurataTurno(int durataTurno) {
        this.durataTurno=durataTurno;
    }
    @Override
    public List<Carta> getMano(){
        return this.mano;
    }
    @Override
    public boolean isTurnoInPausa(){
        return turnoInPausa;

    }
    @Override
    public String getName() {
        return nome;
    }

    @Override
    public void setName(String nome) {
        this.nome = nome;
    }
    @Override
    public int getMaxScore() {
        return maxScore;
    }
    @Override
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
     @Override
    public List<Partita> getPartite() {
        return partite;
    }
    @Override
    public void setPartite(List<Partita> partite) {
        this.partite = partite;
    }
  @Override
    public boolean hasFinishedTurn() {

        throw new UnsupportedOperationException("Unimplemented method 'hasFinishedTurn'");
    }


}
