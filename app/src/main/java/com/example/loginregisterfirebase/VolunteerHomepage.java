package com.example.loginregisterfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class VolunteerHomepage extends AppCompatActivity {

    private WebView webView;


    // Declare your buttons
    private Button mapbtn;
    private Button settingsbtn;
    private Button homebtn;

    // Getter methods for buttons
    public Button getMapButton() {
        return mapbtn;
    }

    public Button getSettingsButton() {
        return settingsbtn;
    }

    public Button getHomeButton() {
        return homebtn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_homepage);

        // Initialize your buttons
        mapbtn = findViewById(R.id.mapBtn_Volunteer);
        settingsbtn = findViewById(R.id.settingsBtn_Volunteer);
        homebtn = findViewById(R.id.homeBtn_Volunteer);

        // Create an instance of VolunteerNavBar
        VolunteerNavBar volunteerNavBar = new VolunteerNavBar();

        // Call the methods of VolunteerNavBar to set click listeners on buttons
        volunteerNavBar.setMapButtonClickListener(this);
        volunteerNavBar.setSettingsButtonClickListener(this);
        volunteerNavBar.setHomeButtonClickListener(this);


//        mapbtn = findViewById(R.id.mapBtn_Volunteer);
//        settingsbtn = findViewById(R.id.settingsBtn_Volunteer);
//        homebtn =findViewById(R.id.homeBtn_Volunteer);


        // Find the WebView object in the layout
        webView = findViewById(R.id.foodDonationWebView);
        webView.setInitialScale(75);

        // Enable JavaScript (optional)
        webView.getSettings().setJavaScriptEnabled(true);

        // Load the URL into the WebView
        webView.loadUrl("https://foodbank.sg/deposit-food/individual-donors/");

        // Set a WebViewClient to handle page navigation (optional)
        webView.setWebViewClient(new WebViewClient());

    }
}

//        mapbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //open Register activity
//                startActivity(new Intent(VolunteerHomepage.this, MapVol.class));
//            }
//        });
//
//        settingsbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //open Register activity
//                startActivity(new Intent(VolunteerHomepage.this,VolunteerSettings.class));
//            }
//        });
//
//        homebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(VolunteerHomepage.this, "You are in the homepage", Toast.LENGTH_SHORT).show();
//            }
//        });





