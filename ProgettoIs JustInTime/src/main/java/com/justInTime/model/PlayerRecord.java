package com.justInTime.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerRecord {

    private String paese;

    private String nome;

    private int maxScore;

    public PlayerRecord(String paese, String nome, int maxScore) {
        this.paese = paese;
        this.nome = nome;
        this.maxScore = maxScore;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
}
