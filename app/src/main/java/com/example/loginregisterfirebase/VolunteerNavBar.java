package com.example.loginregisterfirebase;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class VolunteerNavBar extends VolNavBar {

    @Override
    public void setMapButtonClickListener(final VolunteerHomepage volunteerHomepage) {
        volunteerHomepage.getMapButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open MapVol activity
                volunteerHomepage.startActivity(new Intent(volunteerHomepage, MapVol.class));
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
