package com.justInTime.model;


public class SinglePlayerDataDTO {

    private int partiteGiocate;
    private int vittorie;
    private int maxScore;

  
    public SinglePlayerDataDTO(int partiteGiocate, int vittorie, int maxScore) {
        this.partiteGiocate = partiteGiocate;
        this.vittorie = vittorie;
        this.maxScore = maxScore;
    }

   
    public int getPartiteGiocate() {
        return partiteGiocate;
    }

    public void setPartiteGiocate(int partiteGiocate) {
        this.partiteGiocate = partiteGiocate;
    }

    public int getVittorie() {
        return vittorie;
    }

    public void setVittorie(int vittorie) {
        this.vittorie = vittorie;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

}
