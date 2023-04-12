package com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView;

public class RecyclerViewItems {

    String Address,UnitNumber,BoxPostalCode;
    int Status;

    public RecyclerViewItems(){}

    public RecyclerViewItems(String address, int status, String postalCode, String unitnumber) {
        Address = address;
        Status=status;
        BoxPostalCode = postalCode;
        UnitNumber = unitnumber;
    }

    public String getAddress() {
        return Address;
    }

    public String getBoxPostalCode() {
        return BoxPostalCode;
    }


    public String getUnitNumber() {
        return UnitNumber;
    }

    public int getStatus() {return Status;}
}



