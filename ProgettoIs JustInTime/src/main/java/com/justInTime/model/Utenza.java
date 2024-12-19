package com.justInTime.model;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class Utenza implements UtenzaBridge{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;

    private  String nome;

    private  String cognome;

    private  Date dataNascita;

    private  String paese;

    private String telefono;

    private  String email;

    private  String password;
    private  Date dataCreazioneAccount;
    private  String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Utenza_id", referencedColumnName = "id")
    private Player player;


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }



    public void setPaese(String paese) {
        this.paese = paese;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDataCreazioneAccount(Date dataCreazioneAccount) {
        this.dataCreazioneAccount = dataCreazioneAccount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    private  String tipo;

    private String img;

    public Utenza( String tipo) {
   
        this.tipo = tipo;
    }

    public Utenza(int idUtente, String nome, String cognome, Date dataNascita, String indirizzo, String paese, String telefono, String email, String password, Date dataCreazioneAccount, String username, String tipo, String img) {
     
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.paese = paese;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.dataCreazioneAccount = dataCreazioneAccount;
        this.username = username;
        this.tipo = tipo;
        this.img = img;
    }

    // Getters
    public long getIdUtente() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public String getPaese() {
        return paese;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getDataCreazioneAccount() {
        return dataCreazioneAccount;
    }

    public String getUsername() {
        return username;
    }

    public String getTipo() {
        return tipo;
    }



    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


}
