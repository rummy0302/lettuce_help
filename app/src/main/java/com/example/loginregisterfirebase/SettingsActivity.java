package com.example.loginregisterfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        // Defining all buttons on lower nav bar
        //TODO: implement map asap
        Button mapBtn = (Button)findViewById(R.id.mapbtn);
        Button homeBtn = (Button)findViewById(R.id.homebtn);
//        Button settingsBtn = (Button)findViewById(R.id.settingsbtn);
        Button changePwBtn = (Button)findViewById(R.id.change_password_page);
        Button editNotifications = (Button)findViewById(R.id.edit_notifications);
        Button reportBtn = (Button)findViewById(R.id.report_button);

        // home btn: go to homepage
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(FirebaseAuth.getInstance().getCurrentUser().){
                startActivity(new Intent(SettingsActivity.this, VolunteerHome.class));
                finish();
//            }
            }
        });

        // changePw btn: go to change password page
        changePwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, PasswordSettings.class));
//                finish();
            }
        });

        // report btn: go to issue report page
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, VolunteerReport.class));
                finish();
            }
        });

    }
}




