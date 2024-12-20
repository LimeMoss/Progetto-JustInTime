package com.justInTime.model;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * La classe Utenza rappresenta un'entità utente con dettagli come nome, cognome,
 * data di nascita, paese, e credenziali di accesso. Implementa l'interfaccia {@link UtenzaBridge}.
 */
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
    private LocalDate dataCreazioneAccount;
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Utenza_id", referencedColumnName = "id")
    private Player player;

    /**
     * Costruttore predefinito.
     */
    public Utenza() {}

    /**
     * Costruttore completo per creare un'istanza di Utenza.
     *
     * @param idUtente            l'ID utente.
     * @param nome                il nome dell'utente.
     * @param cognome             il cognome dell'utente.
     * @param dataNascita         la data di nascita dell'utente.
     * @param indirizzo           l'indirizzo dell'utente (non utilizzato nella classe attuale).
     * @param paese               il paese dell'utente.
     * @param telefono            il numero di telefono dell'utente.
     * @param email               l'email dell'utente.
     * @param password            la password dell'utente.
     * @param dataCreazioneAccount la data di creazione dell'account.
     * @param username            il nome utente.
     */
    public Utenza(int idUtente, String nome, String cognome, Date dataNascita, String indirizzo, String paese, String telefono, 
                  String email, String password, LocalDate dataCreazioneAccount, String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.paese = paese;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.dataCreazioneAccount = dataCreazioneAccount;
        this.username = username;
    }

    /**
     * Costruttore minimo con ID, username e password.
     *
     * @param id       l'ID dell'utente.
     * @param username il nome utente.
     * @param pss      la password dell'utente.
     */
    public Utenza(Long id, String username, String pss) {
        this.id = id;
        this.username = username;
        this.password = pss;
    }

    // Getter e Setter

    /**
     * Restituisce il paese dell'utente.
     *
     * @return il paese dell'utente.
     */
    public String getPaese() {
        return paese;
    }

    /**
     * Imposta il paese dell'utente.
     *
     * @param paese il paese da impostare.
     */
    public void setPaese(String paese) {
        this.paese = paese;
    }

    /**
     * Imposta il giocatore associato all'utente.
     *
     * @param player il giocatore da associare.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    // Metodi Override (UtenzaBridge)

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return nome;
    }

    /**
     * Restituisce il cognome dell'utente.
     *
     * @return il cognome dell'utente.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il nome dell'utente.
     *
     * @param nome il nome da impostare.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Imposta il cognome dell'utente.
     *
     * @param cognome il cognome da impostare.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce la data di creazione dell'account.
     *
     * @return la data di creazione dell'account.
     */
    public LocalDate getDataCreazioneAccount() {
        return dataCreazioneAccount;
    }

    /**
     * Imposta la data di creazione dell'account.
     *
     * @param dataCreazioneAccount la data di creazione da impostare.
     */
    public void setDataCreazioneAccount(LocalDate dataCreazioneAccount) {
        this.dataCreazioneAccount = dataCreazioneAccount;
    }

    /**
     * Restituisce il nome utente.
     *
     * @return il nome utente.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Imposta il nome utente.
     *
     * @param username il nome utente da impostare.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Restituisce la data di nascita dell'utente.
     *
     * @return la data di nascita.
     */
    public Date getDataNascita() {
        return dataNascita;
    }

    /**
     * Imposta la data di nascita dell'utente.
     *
     * @param dataNascita la data di nascita da impostare.
     */
    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     * Restituisce il numero di telefono dell'utente.
     *
     * @return il numero di telefono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Imposta il numero di telefono dell'utente.
     *
     * @param telefono il numero di telefono da impostare.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Restituisce l'email dell'utente.
     *
     * @return l'email dell'utente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email dell'utente.
     *
     * @param email l'email da impostare.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce la password dell'utente.
     *
     * @return la password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dell'utente.
     *
     * @param password la password da impostare.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
