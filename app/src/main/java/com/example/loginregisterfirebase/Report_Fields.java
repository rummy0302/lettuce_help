package com.example.loginregisterfirebase;

public class Report_Fields {
    String name;
    String email;

    String contactNumber;

    String details;

    public Report_Fields(String name, String email, String contactNumber, String details) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getDetails() {
        return details;
    }
}
