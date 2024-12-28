package com.justInTime.model;


import java.util.Date;





public interface abstractUtente {

    public String getPaese();

    public void setPaese(String paese);

    public void setPlayer(Player player);

    
    public Long getId();
    
    public String getName();

    public String getCognome();

    public void setNome(String nome);

    public void setCognome(String cognome);

    public String getUsername();

    public void setUsername(String username);

    public Date getDataNascita();

    public void setDataNascita(Date dataNascita);

    public String getTelefono();

    public void setTelefono(String telefono);

    public String getEmail();

    public void setEmail(String email);

    public String getPassword();

    public void setPassword(String password);
}




    


