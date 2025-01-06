package com.justInTime.DTO;

import java.util.Date;

public class FullPlayerDataDTOPsw {
    private String username;
    private String name;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private String paese;
    private Date dataNascita;


    public FullPlayerDataDTOPsw() {}


    public FullPlayerDataDTOPsw(String username, String name, String cognome, String email, String password, String telefono, String paese, Date dataNascita) {
        this.username = username;
        this.name = name;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.paese = paese;
        this.dataNascita = dataNascita;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }


}
