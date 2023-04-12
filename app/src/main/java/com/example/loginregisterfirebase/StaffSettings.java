package com.example.loginregisterfirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StaffSettings extends AppCompatActivity {

    private WebView webView;

    private Button mapBtn,homeBtn,settingsBtn,resetPwd,reportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_settings);

        // Defining all buttons on lower nav bar
        mapBtn = (Button)findViewById(R.id.mapBtn_Volunteer);
        homeBtn = (Button)findViewById(R.id.homeBtn_Volunteer);
        settingsBtn = (Button)findViewById(R.id.settingsBtn_Volunteer);

        // Defining all options
        resetPwd = (Button)findViewById(R.id.resetPasswordPageBtn);
        reportBtn = (Button)findViewById(R.id.reportBtn);

        // home btn: go to homepage
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(FirebaseAuth.getInstance().getCurrentUser().){
                startActivity(new Intent(StaffSettings.this, StaffHomepage.class));
                finish();
//            }
            }
        });

        // changePw btn: go to change password page
        resetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffSettings.this, ResetPassword.class));
//                finish();
            }
        });

        // report btn: go to issue report page
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffSettings.this, StaffReport.class));
                finish();
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StaffSettings.this,"You are in the Settings page",Toast.LENGTH_SHORT).show();
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri.Builder uriBuilder = new Uri.Builder();
                /** geo:0.0?q=ChangiAirport */

                uriBuilder.scheme("geo").opaquePart("0.0")
                        .appendQueryParameter("q","ChangiAirport");

                Uri uri = uriBuilder.build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                if( intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });



    }
}




