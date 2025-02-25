package com.justInTime.model;

import java.util.List;




public interface abstractPlayer{


    public void setUtente(Utente Utente);
    
    public void increaseMaxScore();

    public Long getId();

    public int getDurataTurno();
    public void setDurataTurno(int durataTurno);

    public List<Carta> getMano();

    public int getMaxScore();

    public void setMaxScore(int maxScore);

    
    public List<Partita> getPartite();
    public void setPartite(List<Partita> partite);
    public boolean hasFinishedTurn() ;
    public void dimishTurn();

}