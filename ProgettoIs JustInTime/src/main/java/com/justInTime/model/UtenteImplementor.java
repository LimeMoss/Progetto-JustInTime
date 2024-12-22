package com.justInTime.model;

public class UtenteImplementor implements UserImplementor {
    private String firstName;
    private String lastName;
    private String country;
    private String phone;
    
    @Override
    public String getName() {
        return firstName + " " + lastName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public String getCountry() {
        return country;
    }
    
    @Override
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
}