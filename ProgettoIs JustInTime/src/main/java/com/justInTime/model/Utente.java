package com.justInTime.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Utente extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
    private String password;
    private LocalDate dataCreazioneAccount;
    private String username;
    

    
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    private Player player;

    public Utente(){
        super(new UtenteImplementor());

    }

    public Utente(String nome, String cognome, Date dataNascita, String paese,
    Long telefono, String email, String password, String username) {
super(new UtenteImplementor());
UtenteImplementor impl = (UtenteImplementor)this.implementor;
impl.setNome(nome);
impl.setCognome(cognome);
impl.setPaese(paese);
impl.setTelefono(telefono);
this.email = email;
this.password = password;
this.username = username;
this.dataCreazioneAccount = LocalDate.now();
}
    
    /**
     * Returns the display name of the user, which is the concatenation of the first name and the last name.
     * @return the display name of the user
     */
    @Override
    public String getVisualizzaNome() {
        return implementor.getNome();
    }
    

/**
 * Retrieves the unique identifier of the user.
 * 
 * @return the ID of the user
 */


    /**
     * Retrieves the unique identifier of the user.
     * 
     * @return the ID of the user
     */
    @Override
    public Long getId() {
        return this.id;
    }
    

    /**
     * Sets the name of the user, splitting it into first name and last name using a single space as separator.
     * If the name does not contain any space, the first name will be set to the entire name and the last name will be empty.
     * @param name the name of the user
     */
    @Override
    public void setNome(String name) {
        String[] parts = name.split(" ", 2);
        UtenteImplementor impl = (UtenteImplementor)this.implementor;
        impl.setNome(parts[0]);
        if (parts.length > 1) {
            impl.setCognome(parts[1]);
        }
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
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public LocalDate getDataCreazioneAccount() {
        return dataCreazioneAccount;
    }
    
    public void setDataCreazioneAccount(LocalDate dataCreazioneAccount) {
        this.dataCreazioneAccount = dataCreazioneAccount;
    }


 


}