package com.justInTime.model;

public class PlayerImplementor implements UserImplementor {
    private String nome;
    private String paese;
    
    /**
     * @return il nome del giocatore
     */
    @Override
    public String getNome() {
        return nome;
    }
    

    /**
     * Setta il nome del player.
     * 
     * @param name nome del giocatore
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * @return il paese del giocatore
     */
    @Override
    public String getPaese() {
        return this.paese;
    }
    
    /**
     * Set del paese del giocatore.
     * 
     * @param paese del giocatore
     */
    @Override
    public void setPaese(String paese) {
        this.paese = paese;
    }
}