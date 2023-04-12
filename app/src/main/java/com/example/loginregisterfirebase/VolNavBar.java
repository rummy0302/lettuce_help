package com.example.loginregisterfirebase;
public abstract class VolNavBar {

    // Existing abstract methods
    public abstract void setMapButtonClickListener(final VolunteerHomepage volunteerHomepage);
    public abstract void setSettingsButtonClickListener(final VolunteerHomepage volunteerHomepage);
    public abstract void setHomeButtonClickListener(final VolunteerHomepage volunteerHomepage);

    // Abstract method to be implemented by concrete classes
    public abstract void setProfileButtonClickListener(final VolunteerHomepage volunteerHomepage);
}

