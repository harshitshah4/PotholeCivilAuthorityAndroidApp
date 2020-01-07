package com.example.potholecivilauthorityandroidapp.Models;


import com.google.gson.annotations.SerializedName;

public class CivilAuthority {


    @SerializedName("caid")
    private String caid;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("otp")
    private String otp;

    public CivilAuthority(String email) {
        this.email = email;
    }

    public CivilAuthority(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public CivilAuthority(String firstname, String lastname, String email, String password, String otp) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.otp = otp;
    }

    public CivilAuthority(String caid, String firstname, String lastname, String email) {
        this.caid = caid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public String getCaid() {
        return caid;
    }

    public void setCaid(String caid) {
        this.caid = caid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
