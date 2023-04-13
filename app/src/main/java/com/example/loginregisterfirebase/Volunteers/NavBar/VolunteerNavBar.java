package com.example.loginregisterfirebase.Volunteers.NavBar;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.loginregisterfirebase.Volunteers.NavBar.VolNavBar;
import com.example.loginregisterfirebase.Volunteers.VolunteerHomepage;
import com.example.loginregisterfirebase.Volunteers.VolunteerMaps;
import com.example.loginregisterfirebase.Volunteers.VolunteerSettings;

public class VolunteerNavBar extends VolNavBar {

    @Override
    public void setMapButtonClickListener(final VolunteerHomepage volunteerHomepage) {
        volunteerHomepage.getMapButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open MapVol activity
                volunteerHomepage.startActivity(new Intent(volunteerHomepage, VolunteerMaps.class));
            }
        });
    }

    @Override
    public void setSettingsButtonClickListener(final VolunteerHomepage volunteerHomepage) {
        volunteerHomepage.getSettingsButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open VolunteerSettings activity
                volunteerHomepage.startActivity(new Intent(volunteerHomepage, VolunteerSettings.class));
            }
        });
    }

    @Override
    public void setHomeButtonClickListener(final VolunteerHomepage volunteerHomepage) {
        volunteerHomepage.getHomeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast for homepage
                Toast.makeText(volunteerHomepage, "You are in the homepage", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setProfileButtonClickListener(final VolunteerHomepage volunteerHomepage) {
        // Implementation for setProfileButtonClickListener
        // Add your code here for handling profile button click event
    }
}
