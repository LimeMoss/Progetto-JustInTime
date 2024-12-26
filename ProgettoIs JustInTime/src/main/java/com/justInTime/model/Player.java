package com.justInTime.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Transient
    private int durataTurno;
    
    @Transient
    private boolean turnoInPausa;
    
    @Transient
    private List<Carta> mano;
    
    private int maxScore;
    
    @ManyToMany(mappedBy = "giocatori", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Partita> partite = new ArrayList<>();
    
    public Player() {
        super(new PlayerImplementor());
        this.mano = new ArrayList<>();
    }
    
    public Player(String name, int maxScore) {
        super(new PlayerImplementor());
        ((PlayerImplementor)this.implementor).setNome(name);
        this.maxScore = maxScore;
        this.mano = new ArrayList<>();
    }
    
    @Override
    public String getVisualizzaNome() {
        return implementor.getNome();
    }
    
    @Override
    public Long getId() {
        return this.id;
    }
    

    public void setNome(String name) {
        ((PlayerImplementor)this.implementor).setNome(name);
    }
    
    public void aggiungiCartaAllaMano(Carta carta) {
        this.mano.add(carta);
    }
    
    public boolean rimuoviCartaDallaMano(Carta carta) {
        return this.mano.remove(carta);
    }
    
    public void increaseMaxScore() {
        this.maxScore++;
    }
    
    public int getDurataTurno() {
        return this.durataTurno;
    }
    
    public void setDurataTurno(int durataTurno) {
        this.durataTurno = durataTurno;
    }
    
    public List<Carta> getMano() {
        return this.mano;
    }
    
    public boolean isTurnoInPausa() {
        return turnoInPausa;
    }
    
    public int getMaxScore() {
        return maxScore;
    }
    
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTurnoInPausa(boolean turnoInPausa) {
        this.turnoInPausa = turnoInPausa;
    }

    public void setMano(List<Carta> mano) {
        this.mano = mano;
    }

    public List<Partita> getPartite() {
        return partite;
    }

    public void setPartite(List<Partita> partite) {
        this.partite = partite;
    }

    public boolean hasFinishedTurn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasFinishedTurn'");
    }



}