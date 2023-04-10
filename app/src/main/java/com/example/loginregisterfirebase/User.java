package com.example.loginregisterfirebase;

public class User {
     String fullname;
     String email;
     String ContactNumber;
     String Password;
     String ConPassword;

     int UserType;

    public User(String fullname, String email, String PhoneNumber, String Password, String ConPassword, int UserType) {
        this.fullname = fullname;
        this.email = email;
        this.ContactNumber = PhoneNumber;
        this.Password = Password;
        this.ConPassword = ConPassword;
        this.UserType = UserType;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public String getPassword() {
        return Password;
    }

    public String getConPassword() {
        return ConPassword;
    }
}
