package com.justInTime.model;

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
    private Date dataCreazioneAccount;
    private String username;
    private String tipo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Utenza_id", referencedColumnName = "id")
    private Player player;

    // Costruttori
    public Utenza() {}

    public Utenza(String tipo) {
        this.tipo = tipo;
    }

    public Utenza(int idUtente, String nome, String cognome, Date dataNascita, String indirizzo, String paese, String telefono, 
                  String email, String password, Date dataCreazioneAccount, String username, String tipo, String img) {
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
}
