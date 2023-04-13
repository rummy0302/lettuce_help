package com.example.loginregisterfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class VolunteerSettings extends AppCompatActivity {

    private WebView webView;

    private Button mapBtn,homeBtn,settingsBtn,resetPwd,reportBtn,signoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_settings);

        // Defining all buttons on lower nav bar
        mapBtn = (Button)findViewById(R.id.mapBtn_Volunteer);
        homeBtn = (Button)findViewById(R.id.homeBtn_Volunteer);
        settingsBtn = (Button)findViewById(R.id.settingsBtn_Volunteer);
        signoutBtn=(Button)findViewById(R.id.signout);

        // Defining all options
        resetPwd = (Button)findViewById(R.id.resetPasswordPageBtn);
        reportBtn = (Button)findViewById(R.id.reportBtn);

        // home btn: go to homepage
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(FirebaseAuth.getInstance().getCurrentUser().){
                startActivity(new Intent(VolunteerSettings.this, VolunteerHomepage.class));
                finish();
//            }
            }
        });

        // changePw btn: go to change password page
        resetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VolunteerSettings.this, VolunteerResetPassword.class));
//                finish();
            }
        });

        // report btn: go to issue report page
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VolunteerSettings.this, VolunteerReport.class));
                finish();
            }
        });

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(VolunteerSettings.this,"Signout Succesful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(VolunteerSettings.this, Login.class));
                finish();
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VolunteerSettings.this,"You are in the Settings page",Toast.LENGTH_SHORT).show();
            }
        });

//        mapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(VolunteerSettings.this, VolunteerMaps.class));
//            }
//        });

        //TODO:UI and maps


    }
}




