package com.example.loginregisterfirebase.Volunteers;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Volunteers.NavBar.NavBarListener;
import com.example.loginregisterfirebase.Volunteers.NavBar.VolunteerNavBar;

public class VolunteerHomePage extends AppCompatActivity {

    private WebView webView;
    private NavBarListener navBar;
    private Button mapbtn,homebtn,settingsbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_homepage);


        //==================== NAV BAR  ================//
        mapbtn = findViewById(R.id.mapBtn_Volunteer);
        homebtn=findViewById(R.id.homeBtn_Volunteer);
        settingsbtn=findViewById(R.id.settingsBtn_Volunteer);
        navBar= new VolunteerNavBar();

        navBar.onHomeButtonClick(homebtn,VolunteerHomePage.this);
        navBar.onMapButtonClick(mapbtn,VolunteerHomePage.this);
        navBar.onSettingsButtonClick(settingsbtn,VolunteerHomePage.this);


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





