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
    String telefono, String email, String password, String username) {
super(new UtenteImplementor());
UtenteImplementor impl = (UtenteImplementor)this.implementor;
impl.setFirstName(nome);
impl.setLastName(cognome);
impl.setCountry(paese);
impl.setPhone(telefono);
this.email = email;
this.password = password;
this.username = username;
this.dataCreazioneAccount = LocalDate.now();
}
    
    @Override
    public String getDisplayName() {
        return implementor.getName();
    }
    
    @Override
    public Long getId() {
        return this.id;
    }
    
    @Override
    public void setName(String name) {
        String[] parts = name.split(" ", 2);
        UtenteImplementor impl = (UtenteImplementor)this.implementor;
        impl.setFirstName(parts[0]);
        if (parts.length > 1) {
            impl.setLastName(parts[1]);
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