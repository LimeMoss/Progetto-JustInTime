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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
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

    private String nome;



    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public boolean isEscluso() {
        return escluso;
    }


    public void setEscluso(boolean escluso) {
        this.escluso = escluso;
    }


    public List<Achievements> getAchievements() {
        return achievements;
    }


    public void setAchievements(List<Achievements> achievements) {
        this.achievements = achievements;
    }
    private boolean escluso;



    @ManyToMany(mappedBy = "giocatori", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Partita> partite = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "utente_id", referencedColumnName = "id")
    private Utente utente;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "giocatori_achievement",
        joinColumns = @JoinColumn(name = "player_id"),
        inverseJoinColumns = @JoinColumn(name = "achievement_id")
    )
    private List<Achievements> achievements;


    private int maxScore;

    // Costruttori
    public Player() {
        mano = new ArrayList<Carta>();
        this.durataTurno = 15;
    }


    public Player(String name, int maxScore) {
        this.nome=name;
        this.maxScore = maxScore;
        this.durataTurno = 15;
        mano = new ArrayList<Carta>();
    }

    public Player(String name) {
        this.nome=name;
        this.durataTurno = 15;

        mano = new ArrayList<Carta>();
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






    public Utente getUtente() {
        return utente;
    }


    public void setUtente(Utente utente) {
        this.utente = utente;
    }


    // Metodi Utility



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

        return this.turnoInPausa;
    }




}
