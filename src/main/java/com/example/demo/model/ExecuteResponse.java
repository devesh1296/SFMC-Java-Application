package com.example.demo.model;

public class ExecuteResponse {
    private String foundSignupDate;
    private String alternateSignupDate;

    public String getFoundSignupDate() {
        return foundSignupDate;
    }

    public String getAlternateSignupDate() {return alternateSignupDate;}

    public void setFoundSignupDate(String foundSignupDate) {
        this.foundSignupDate = foundSignupDate;
    }

    public void setAlternateSignupDate(String alternateSignupDate) {this.alternateSignupDate = alternateSignupDate;}
}
