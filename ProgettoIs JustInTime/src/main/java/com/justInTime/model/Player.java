
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
public class Player implements UtenzaBridge  {

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


    public List<Partita> getPartite() {
    return partite;
}

    public int getDurataTurno() {
        return durataTurno;
    }

    public void setDurataTurno(int durataTurno) {
        this.durataTurno = durataTurno;
    }

    public boolean isTurnoInPausa() {
        return turnoInPausa;
    }

    public void setTurnoInPausa(boolean turnoInPausa) {
        this.turnoInPausa = turnoInPausa;
    }

    public void setPartite(List<Partita> partite) {
    this.partite = partite;
}

    private int maxScore;  


    public Player() {
        mano = new ArrayList<Carta>();
    }

    public Player(String name, int maxScore) {
        this.name = name;
        this.maxScore = maxScore;
        mano = new ArrayList<Carta>();
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore)
    {
        this.maxScore=maxScore;

    }
    public List<Carta> getMano() {
        return mano;
    }

    public void setMano(List<Carta> mano) {
        this.mano = mano;
    }

   
    public void aggiungiCartaAllaMano(Carta carta) {
        this.mano.add(carta);
    }

  
    public boolean rimuoviCartaDallaMano(Carta carta) {
        return this.mano.remove(carta);
    }


    public void IncreaseMaxScore() {
            this.maxScore++;  
        }


    }