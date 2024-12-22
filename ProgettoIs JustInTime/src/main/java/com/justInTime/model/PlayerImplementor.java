package com.justInTime.model;

public class PlayerImplementor implements UserImplementor {
    private String name;
    private String country;
    
    @Override
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getCountry() {
        return country;
    }
    
    @Override
    public void setCountry(String country) {
        this.country = country;
    }
}