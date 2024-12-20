package com.justInTime.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un giocatore in una partita.
 * Un giocatore ha un nome, una mano di carte, un punteggio massimo, e può partecipare a più partite.
 * Inoltre, può essere associato a un'utenza che definisce il paese del giocatore.
 */
@Entity
public class Player implements UtenzaBridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Transient // Non persiste la mano nel database
    private List<Carta> mano = new ArrayList<>();

    private int durataTurno;

    private boolean turnoFinito;
    private boolean turnoInPausa;

    private int maxScore;

    private String paese;

    @ManyToMany(mappedBy = "giocatori", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Partita> partite = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "utenza_id")
    private UtenzaBridge utenzaBridge;

    // Costruttori

    /**
     * Costruttore vuoto per la creazione di un oggetto Player.
     * È necessario per la persistenza JPA.
     */
    public Player() {}

    /**
     * Costruttore per creare un giocatore con un nome e un punteggio massimo.
     *
     * @param name Il nome del giocatore.
     * @param maxScore Il punteggio massimo del giocatore.
     */
    public Player(String name, int maxScore) {
        this.name = name;
        this.maxScore = maxScore;
        this.turnoFinito = false;
        this.turnoInPausa = false;
        this.mano = new ArrayList<>();
    }

    // Getter e Setter

    /**
     * Ottiene l'ID del giocatore.
     * 
     * @return L'ID del giocatore.
     */
    public Long getId() {
        return id;
    }

    /**
     * Imposta l'ID del giocatore.
     * 
     * @param id L'ID da impostare.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Ottiene il nome del giocatore.
     * 
     * @return Il nome del giocatore.
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome del giocatore.
     * 
     * @param name Il nome da impostare.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Ottiene la mano di carte del giocatore.
     * 
     * @return La lista di carte nella mano del giocatore.
     */
    public List<Carta> getMano() {
        return mano;
    }

    /**
     * Imposta la mano di carte del giocatore.
     * 
     * @param mano La lista di carte da impostare.
     */
    public void setMano(List<Carta> mano) {
        this.mano = mano;
    }

    /**
     * Ottiene la durata del turno del giocatore.
     * 
     * @return La durata del turno.
     */
    public int getDurataTurno() {
        return durataTurno;
    }

    /**
     * Imposta la durata del turno del giocatore.
     * 
     * @param durataTurno La durata del turno da impostare.
     */
    public void setDurataTurno(int durataTurno) {
        this.durataTurno = durataTurno;
    }

    /**
     * Verifica se il turno del giocatore è in pausa.
     * 
     * @return true se il turno è in pausa, false altrimenti.
     */
    public boolean isTurnoInPausa() {
        return turnoInPausa;
    }

    /**
     * Imposta lo stato del turno come in pausa.
     * 
     * @param turnoInPausa true se il turno è in pausa, false altrimenti.
     */
    public void setTurnoInPausa(boolean turnoInPausa) {
        this.turnoInPausa = turnoInPausa;
    }

    /**
     * Verifica se il giocatore ha finito il suo turno.
     * 
     * @return true se il turno è finito, false altrimenti.
     */
    public boolean hasFinishedTurn() {
        return turnoFinito;
    }

    /**
     * Imposta lo stato del turno come finito.
     * 
     * @param turnoFinito true se il turno è finito, false altrimenti.
     */
    public void setTurnoFinito(boolean turnoFinito) {
        this.turnoFinito = turnoFinito;
    }

    /**
     * Resetta lo stato del turno, segnando che il turno non è finito e non è in pausa.
     */
    public void resetTurno() {
        this.turnoFinito = false;
        this.turnoInPausa = false;
    }

    /**
     * Ottiene il punteggio massimo del giocatore.
     * 
     * @return Il punteggio massimo del giocatore.
     */
    public int getMaxScore() {
        return maxScore;
    }

    /**
     * Imposta il punteggio massimo del giocatore.
     * 
     * @param maxScore Il punteggio massimo da impostare.
     */
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * Ottiene il paese del giocatore.
     * 
     * @return Il paese del giocatore.
     */
    public String getPaese() {
        return paese;
    }

    /**
     * Imposta il paese del giocatore.
     * 
     * @param paese Il paese da impostare.
     */
    public void setPaese(String paese) {
        this.paese = paese;
    }

    // Metodi per la gestione delle carte

    /**
     * Aggiunge una carta alla mano del giocatore.
     * 
     * @param carta La carta da aggiungere.
     */
    public void aggiungiCartaAllaMano(Carta carta) {
        mano.add(carta);
    }

    /**
     * Rimuove una carta dalla mano del giocatore.
     * 
     * @param carta La carta da rimuovere.
     * @return true se la carta è stata rimossa, false altrimenti.
     */
    public boolean rimuoviCartaDallaMano(Carta carta) {
        return mano.remove(carta);
    }

    /**
     * Incrementa il punteggio massimo del giocatore di uno.
     */
    public void increaseMaxScore() {
        this.maxScore++;
    }

    // Metodi per associare un Player con un'utenza

    /**
     * Associa un'utenza al giocatore.
     * 
     * @param utenza L'utenza da associare.
     */
    public void associaUtenza(Utenza utenza) {
        this.utenzaBridge = utenza; // Associa l'utenza al player
        if (utenza != null) {
            this.paese = utenza.getPaese(); // Imposta il paese del player uguale a quello dell'utenza
        }
    }

    // Metodi Override (UtenzaBridge)

    /**
     * Ottiene l'ID dell'utenza associata al giocatore.
     * 
     * @return L'ID dell'utenza.
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Ottiene il nome del giocatore.
     * 
     * @return Il nome del giocatore.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome del giocatore.
     * 
     * @param nome Il nome da impostare.
     */
    public void setNome(String nome) {
        this.name = nome;
    }
}
