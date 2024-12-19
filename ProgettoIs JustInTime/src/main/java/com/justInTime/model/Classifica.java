package com.justInTime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

public class Classifica {


    private List<Player> giocatori;

    public Classifica() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Player> getGiocatori() {
        return giocatori;
    }

    public void setGiocatori(List<Player> giocatori) {
        this.giocatori = giocatori;
    }

}
