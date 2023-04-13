package com.example.loginregisterfirebase.Volunteers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Volunteers.NavBar.VolunteerNavBar;

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





