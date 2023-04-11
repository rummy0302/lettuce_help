package com.example.loginregisterfirebase;

public class Registration_Fields {
     String fullname;
     String email;
     String ContactNumber;

     int UserType;

    public Registration_Fields(String fullname, String email, String PhoneNumber, int UserType) {
        this.fullname = fullname;
        this.email = email;
        this.ContactNumber = PhoneNumber;
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

    public int getUserType() {
        return UserType;
    }
}
