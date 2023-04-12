package com.example.loginregisterfirebase;

public class RecyclerViewItems {

    //Address, Postalcode and Status are keywords in the database
    String Address,UnitNumber,BoxPostalCode;
    int Status;


    public RecyclerViewItems(){}

    public RecyclerViewItems(String address, int status, String postalCode, String unitnumber) {
        Address = address;
        Status = status;
        BoxPostalCode = postalCode;
        UnitNumber = unitnumber;
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

    public String getUnitNumber() {
        return UnitNumber;
    }
}



