package com.justInTime.model;

public abstract class User {
    protected UserImplementor implementor;
    
    public User(UserImplementor implementor) {
        this.implementor = implementor;
    }
    
    public abstract String getVisualizzaNome();
    public abstract Long getId();
    public abstract void setNome(String name);
    
    public String getPaese() {
        return implementor.getPaese();
    }
    
    public void setPaese(String paese) {
        implementor.setPaese(paese);
    }
}