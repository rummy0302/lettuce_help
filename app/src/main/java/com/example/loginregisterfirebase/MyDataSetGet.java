package com.example.loginregisterfirebase;

public class MyDataSetGet {

    //Address, Postalcode and Status are keywords in the database
    String Address;
    String Status;
    String BoxPostalCode;

    public MyDataSetGet(){}

    public MyDataSetGet(String address, String status, String postalCode) {
        Address = address;
        Status = status;
        BoxPostalCode = postalCode;
    }

    public String getAddress() {
        return Address;
    }

    public String getBoxPostalCode() {
        return BoxPostalCode;
    }

    public String getStatus() {
        return Status;
    }

}



