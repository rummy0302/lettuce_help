package com.example.loginregisterfirebase.Volunteers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginregisterfirebase.Login;
import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Validator.EmailValidator;
import com.example.loginregisterfirebase.Validator.PasswordValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class VolunteerResetPassword extends AppCompatActivity implements EmailValidator {

    private EditText emailEditText;
    private Button changePasswordButton,mapbtn,homebtn,settingsbtn;

    private String emailString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_reset_password);

        emailEditText = findViewById(R.id.emailResetPassword) ;
        changePasswordButton = findViewById(R.id.updatePasswordBtn);



        //==================== NAV BAR  ================//
        Button mapbtn = findViewById(R.id.mapBtn_Volunteer);
        Button homebtn=findViewById(R.id.homeBtn_Volunteer);
        Button settingsbtn=findViewById(R.id.settingsBtn_Volunteer);

        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VolunteerResetPassword.this, VolunteerMaps.class));
            }
        });

        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VolunteerResetPassword.this,VolunteerSettings.class));
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VolunteerResetPassword.this, VolunteerHomePage.class));

            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ValidateEmail(emailEditText)){
                    changepassword();
                }
            }

            private void changepassword(){
                FirebaseAuth auth= FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(emailEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(VolunteerResetPassword.this,"Check your email",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(VolunteerResetPassword.this, Login.class));
                            finish();
                        }else{
                            Toast.makeText(VolunteerResetPassword.this,"Error :"+ task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }

        });
    }

    @Override
    public Boolean ValidateEmail(EditText e) {
        if (e.getText().toString().isEmpty()) {
            Toast.makeText(VolunteerResetPassword.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            e.setError("Email is required");
            e.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(e.getText().toString()).matches()) {
            Toast.makeText(VolunteerResetPassword.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
            e.setError("Valid email is required");
            e.requestFocus();
            return false;
        }else {
            return true;
        }
    }
}