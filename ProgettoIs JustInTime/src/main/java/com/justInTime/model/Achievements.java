package com.justInTime.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Achievements{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descrizione;
    private boolean unlocked;

    @ManyToMany(mappedBy = "achievements", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Player> players= new ArrayList<>();

    public Achievements(){

    }

    public Achievements(String nome, String descrizione){

        this.nome=nome;
        this.descrizione=descrizione;
        this.unlocked=false;
    }

      public Achievements(String nome, String descrizione, boolean unlocked){

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