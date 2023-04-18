package com.example.loginregisterfirebase.Staff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginregisterfirebase.Login;
import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Register;
import com.google.firebase.auth.FirebaseAuth;

public class StaffSettings extends AppCompatActivity {

    private WebView webView;

    private Button mapBtn,homeBtn,settingsBtn,resetPwd,reportBtn,signoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_settings);

        // Defining all options
        resetPwd = (Button)findViewById(R.id.resetPasswordPageBtn);
        reportBtn = (Button)findViewById(R.id.reportBtn);
        signoutBtn=(Button)findViewById(R.id.signout);


        // changePw btn: go to change password page
        resetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffSettings.this, StaffResetPassword.class));
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

        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(StaffSettings.this,"Signout Succesful",Toast.LENGTH_SHORT).show();
                SharedPreferences sharedpreferences = getSharedPreferences(Login.SHARED_PREFS, android.content.Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();
                finish();
                startActivity(new Intent(StaffSettings.this, Login.class));

            }
        });
    }
}




