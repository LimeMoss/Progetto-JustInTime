package com.justInTime.model;

import java.util.List;




public interface abstractPlayer{


    public void associaUtente(Utente Utente);
    
    public void aggiungiCartaAllaMano(Carta carta);

    public boolean rimuoviCartaDallaMano(Carta carta);

    public void increaseMaxScore();

    public Long getId();

    public int getDurataTurno();
    public void setDurataTurno(int durataTurno);

    public List<Carta> getMano();

    public boolean isTurnoInPausa();

    public int getMaxScore();

    public void setMaxScore(int maxScore);

    public String getName();

    public void setName(String nome);
    
    public List<Partita> getPartite();
    public void setPartite(List<Partita> partite);
    public boolean hasFinishedTurn() ;

}