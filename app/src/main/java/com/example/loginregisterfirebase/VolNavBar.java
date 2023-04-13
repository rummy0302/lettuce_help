package com.example.loginregisterfirebase;

import android.content.Intent;
import android.view.View;

public abstract class VolNavBar {

    // Existing abstract methods
    public void setMapButtonClickListener(final VolunteerHomepage volunteerHomepage) {}

    public abstract void setSettingsButtonClickListener(final VolunteerHomepage volunteerHomepage);
    public abstract void setHomeButtonClickListener(final VolunteerHomepage volunteerHomepage);

    // Abstract method to be implemented by concrete classes
    public abstract void setProfileButtonClickListener(final VolunteerHomepage volunteerHomepage);
}



