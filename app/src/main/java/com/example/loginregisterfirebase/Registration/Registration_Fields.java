package com.example.loginregisterfirebase.Registration;

public class Registration_Fields {
     String fullname;
     String email;
     String ContactNumber;

    public Registration_Fields(String fullname, String email, String PhoneNumber) {
        this.fullname = fullname;
        this.email = email;
        this.ContactNumber = PhoneNumber;

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


}
