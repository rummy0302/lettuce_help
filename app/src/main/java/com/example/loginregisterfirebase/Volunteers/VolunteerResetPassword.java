package com.example.loginregisterfirebase.Volunteers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginregisterfirebase.Login;
import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Validator.EmailValidator;
import com.example.loginregisterfirebase.Volunteers.NavBar.NavBarListener;
import com.example.loginregisterfirebase.Volunteers.NavBar.VolunteerNavBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class VolunteerResetPassword extends AppCompatActivity {

    private EditText emailEditText;
    private Button changePasswordButton,mapbtn,homebtn,settingsbtn;
    private NavBarListener navBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_reset_password);

        emailEditText = findViewById(R.id.emailResetPassword) ;
        changePasswordButton = findViewById(R.id.updatePasswordBtn);

        mapbtn = findViewById(R.id.mapBtn_Volunteer);
        homebtn=findViewById(R.id.homeBtn_Volunteer);
        settingsbtn=findViewById(R.id.settingsBtn_Volunteer);
        navBar=new VolunteerNavBar();

        //navBar
        navBar.onHomeButtonClick(homebtn,VolunteerResetPassword.this);
        navBar.onMapButtonClick(mapbtn,VolunteerResetPassword.this);
        navBar.onSettingsButtonClick(settingsbtn,VolunteerResetPassword.this);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmailValidator emailValidator= new EmailValidator();
                if(emailValidator.Validate(emailEditText,VolunteerResetPassword.this)){
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


}