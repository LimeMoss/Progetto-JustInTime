package com.justInTime.model;

public class paeseUtenzaDTO {


    private String paese;
    private String  nome;

  
    public paeseUtenzaDTO(String paese, String nome) {
       this.nome=nome;
       this.paese=paese;
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

   

}
