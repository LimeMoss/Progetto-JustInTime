package com.justInTime.model;

import jakarta.persistence.*;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Utente implements abstractUtente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;


    

    private String nome;
    private String cognome;
    private Date dataNascita;
    private String paese;
    private String telefono;
    private String email;
    private String password;
    private String username;
  

    @OneToOne(mappedBy = "utente", cascade = CascadeType.REMOVE,  orphanRemoval = true)  
    @PrimaryKeyJoinColumn
    private Player player;

    @Transient
    private abstractPlayer abstractPlayer;


    public Utente() {}

    public Utente(String nome, String cognome, Date dataNascita, String paese, String telefono, 
                  String email, String password, String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.paese = paese;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.username = username;
      
    }



    @Override
    public String getPaese() {
        return paese;
    }


    @Override
    public void setPaese(String paese) {
        this.paese = paese;
    }
    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return nome;
    }
    @Override
    public String getCognome() {
        return cognome;
    }
    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public Date getDataNascita() {
        return dataNascita;
    }
    @Override
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }
    @Override
    public String getTelefono() {
        return telefono;
    }
    @Override
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id){
        this.id=id;
    }

    public void setId(long l) {
        this.id=l;
    }

    public Player getPlayer(){
        return this.player;
    }
}

