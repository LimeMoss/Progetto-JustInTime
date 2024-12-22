package com.justInTime.model;

public abstract class User(){
    private UserImplementor implementor;

    public User (UserImplementor implementor){
        this.implementor=implementor;
    }
    
    public abstract String getDisplayName();
    public abstract String getName();
    public abstract Long getId();
    
}

