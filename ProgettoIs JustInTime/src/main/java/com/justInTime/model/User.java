package com.justInTime.model;

public abstract class User {
    protected UserImplementor implementor;
    
    public User(UserImplementor implementor) {
        this.implementor = implementor;
    }
    
    public abstract String getDisplayName();
    public abstract Long getId();
    public abstract void setName(String name);

    
    public String getCountry() {
        return implementor.getCountry();
    }
    
    public void setCountry(String country) {
        implementor.setCountry(country);
    }
}