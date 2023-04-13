package com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView;

/****
 * The recyclerViewItems correspond to the keys in RealtimeDatabase
 */
public class RecyclerViewItems {


    String Address,UnitNumber,BoxPostalCode;
    int Status;
    Boolean Attending;

    public RecyclerViewItems(){}

    public RecyclerViewItems(String address, int status, String postalCode, String unitnumber,Boolean attending) {
        Address = address;
        Status=status;
        BoxPostalCode = postalCode;
        UnitNumber = unitnumber;
        Attending = attending;
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

    public Boolean getAttending() {
        return Attending;
    }
}



