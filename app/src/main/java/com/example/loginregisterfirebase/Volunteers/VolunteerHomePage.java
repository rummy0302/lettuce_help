package com.example.loginregisterfirebase.Volunteers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginregisterfirebase.R;

public class VolunteerHomePage extends AppCompatActivity {

    private WebView webView;


    // key for stored email.
    public static final String EMAIL_KEY = "email_key";

    // key for stored password.
    public static final String PASSWORD_KEY = "password_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_homepage);

        //==================== NAV BAR  ================//
        Button mapbtn = findViewById(R.id.mapBtn_Volunteer);
        Button homebtn=findViewById(R.id.homeBtn_Volunteer);
        Button settingsbtn=findViewById(R.id.settingsBtn_Volunteer);

        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VolunteerHomePage.this, VolunteerMaps.class));
            }
        });

        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VolunteerHomePage.this,VolunteerSettings.class));
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VolunteerHomePage.this,"You are in the Home page",Toast.LENGTH_SHORT).show();

            }
        });



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





