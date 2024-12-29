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
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class Achievement{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descrizione;
    private boolean unlocked;

    @ManyToMany(mappedBy = "achievements", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Player> players= new ArrayList<>();

    public Achievement(){

    }

    public Achievement(String nome, String descrizione){

        this.nome=nome;
        this.descrizione=descrizione;
        this.unlocked=false;
    }

      public Achievement(String nome, String descrizione, boolean unlocked){

        this.nome=nome;
        this.descrizione=descrizione;
        this.unlocked=unlocked;
    }


    public void setNome(String nome){
        this.nome=nome;
    }

    public void setDescrizione(String descrizione){
        this.descrizione=descrizione;
    }

    public void Unlock(){
        this.unlocked=true;
    }

    public String getNome(){
        return this.nome;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public boolean getUnlocked(){
        return this.unlocked;
    }



}