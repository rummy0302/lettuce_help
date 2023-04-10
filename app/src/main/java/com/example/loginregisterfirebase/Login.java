package com.example.loginregisterfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class  Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userid = findViewById(R.id.userid);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);

        final Button  BSbtn = findViewById(R.id.Box_status_btn);

        final Button Volunteerbtn = findViewById(R.id.volunteerbtn);
        final Button Staffbtn = findViewById(R.id.staffbtn);
        BSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,StaffBoxStatusActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userTxt = userid.getText().toString();
                final String passwordTxt = password.getText().toString();

                if(userTxt.isEmpty()){
                    Toast.makeText(Login.this,"Please enter your email",Toast.LENGTH_SHORT).show();
                } else if (passwordTxt.isEmpty()) {
                    Toast.makeText(Login.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                } else{
                }
            }
        });

        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open Register activity
                startActivity(new Intent(Login.this,Register.class));
            }
        });

        Volunteerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open Register activity
                startActivity(new Intent(Login.this,VolunteerHomepage.class));
            }
        });

        Staffbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open Register activity
                startActivity(new Intent(Login.this,StaffHomepage.class));
            }
        });

    }
}