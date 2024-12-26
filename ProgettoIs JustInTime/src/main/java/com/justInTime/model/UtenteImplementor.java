package com.justInTime.model;

public class UtenteImplementor implements UserImplementor {
    private String nome;
    private String cognome;
    private String paese;
    private Long telefono;
    
    @Override
    public String getNome() {
        return nome + " " + cognome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    
    @Override
    public String getPaese() {
        return paese;
    }
    
    @Override
    public void setPaese(String paese) {
        this.paese = paese;
    }
    
    public Long getTelefono() {
        return telefono;
    }
    
    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }
}