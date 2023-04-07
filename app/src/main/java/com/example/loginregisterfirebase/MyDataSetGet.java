package com.example.loginregisterfirebase;

public class MyDataSetGet {

    //Address, Postalcode and Status are keywords in the database
    String Address;
    int Status;
    String BoxPostalCode;

    public MyDataSetGet(){}

    public MyDataSetGet(String address, int status, String postalCode) {
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

    public Integer getStatus() {
        return Status;
    }

}



