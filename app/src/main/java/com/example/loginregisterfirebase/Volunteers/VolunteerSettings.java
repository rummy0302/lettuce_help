package com.example.loginregisterfirebase.Volunteers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginregisterfirebase.Login;
import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Volunteers.NavBar.NavBarListener;
import com.example.loginregisterfirebase.Volunteers.NavBar.VolunteerNavBar;
import com.google.firebase.auth.FirebaseAuth;

public class VolunteerSettings extends AppCompatActivity {

    private WebView webView;

    private Button mapBtn,homeBtn,settingsBtn,resetPwd,reportBtn,signoutBtn;
    private NavBarListener navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_settings);

        // Defining all buttons on lower nav bar
        mapBtn = (Button)findViewById(R.id.mapBtn_Volunteer);
        homeBtn = (Button)findViewById(R.id.homeBtn_Volunteer);
        settingsBtn = (Button)findViewById(R.id.settingsBtn_Volunteer);
        navBar=new VolunteerNavBar();


        // Defining all options
        resetPwd = (Button)findViewById(R.id.resetPasswordPageBtn);
        reportBtn = (Button)findViewById(R.id.reportBtn);
        signoutBtn=(Button)findViewById(R.id.signout);



        //navBar
        navBar.onHomeButtonClick(homeBtn,VolunteerSettings.this);
        navBar.onMapButtonClick(mapBtn,VolunteerSettings.this);
        navBar.onSettingsButtonClick(settingsBtn,VolunteerSettings.this);



        resetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VolunteerSettings.this, VolunteerResetPassword.class));
            }
        });


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
                SharedPreferences sharedpreferences = getSharedPreferences(Login.SHARED_PREFS, android.content.Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(VolunteerSettings.this, Login.class));
                finish();
            }
        });

    }
}




