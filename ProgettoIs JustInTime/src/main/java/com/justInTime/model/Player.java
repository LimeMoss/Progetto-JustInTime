package com.justInTime.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

    private int vittorie;
    private int partiteGiocate;

    public int getVittorie() {
        return vittorie;
    }

    public void setVittorie(int vittorie) {
        this.vittorie = vittorie;
    }

    public int getPartiteGiocate() {
        return this.partiteGiocate;
    }

    public void setPartiteGiocate(int partiteGiocate) {
        this.partiteGiocate = partiteGiocate;
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

    @Transient
    private boolean escluso;

    @ManyToMany(mappedBy = "giocatori", fetch = FetchType.EAGER)
    private List<Partita> partite;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "utente_id", referencedColumnName = "id")
    private Utente utente;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "giocatori_achievement", joinColumns = @JoinColumn(name = "player_id"), inverseJoinColumns = @JoinColumn(name = "achievement_id"))
    private List<Achievements> achievements;

    @Column(name = "max_score")
    private int maxScore;

    public Player() {
        this.mano = new ArrayList<>();
        this.durataTurno = 15;
        this.partiteGiocate = 0;
        this.vittorie = 0;
        this.escluso = false;
        partite= new ArrayList<>();
        this.turnoInPausa = false;
    }

    public Player(int maxScore) {

        this.maxScore = maxScore;
        this.durataTurno = 15;
        this.mano = new ArrayList<>();
        this.partiteGiocate = 0;
        this.vittorie = 0;
        this.escluso = false;
        partite= new ArrayList<>();
        this.turnoInPausa = false;
    }

    public Player(String jackson) {

        this.durataTurno = 15;
        this.escluso = false;
        partite= new ArrayList<>();
        this.turnoInPausa = false;

        mano = new ArrayList<>();
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

    @Override
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    // Metodi Utility
    @Override
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
        this.durataTurno = durataTurno;
    }

    @Override
    public List<Carta> getMano() {
        return this.mano;
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

    @Override
    public void dimishTurn(){
        this.durataTurno=this.durataTurno-1;
        }

}
