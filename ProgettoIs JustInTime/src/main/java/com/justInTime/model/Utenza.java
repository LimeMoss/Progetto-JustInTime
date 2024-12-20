package com.justInTime.model;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Utenza implements UtenzaBridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    private Date dataNascita;
    private String paese;
    private String telefono;
    private String email;
    private String password;
    private LocalDate dataCreazioneAccount;
    private String username;
  

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Utenza_id", referencedColumnName = "id")
    private Player player;

    // Costruttori
    public Utenza() {}

    public Utenza(int idUtente, String nome, String cognome, Date dataNascita, String indirizzo, String paese, String telefono, 
                  String email, String password, LocalDate dataCreazioneAccount, String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.paese = paese;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.dataCreazioneAccount = dataCreazioneAccount;
        this.username = username;
      
    }

    public Utenza(Long id,String username, String pss) {

        this.id=id;
        this.username= username;
        this.password=pss;
    }

    // Getter e Setter
    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // Metodi Override (UtenzaBridge)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataCreazioneAccount() {
        return dataCreazioneAccount;
    }

    public void setDataCreazioneAccount(LocalDate dataCreazioneAccount) {
        this.dataCreazioneAccount = dataCreazioneAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
