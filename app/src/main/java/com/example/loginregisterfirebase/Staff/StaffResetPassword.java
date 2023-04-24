package com.example.loginregisterfirebase.Staff;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class StaffResetPassword extends AppCompatActivity  {

    private EditText emailEditText;
    private Button changePasswordButton,mapStaffBtn,boxStaffBtn,settingStaffBtn;
    private String emailString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_reset_password);

        emailEditText = findViewById(R.id.emailResetPassword) ;
        changePasswordButton = findViewById(R.id.updatePasswordBtn);



        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmailValidator emailValidator= new EmailValidator();
                if(emailValidator.Validate(emailEditText, StaffResetPassword.this)){
                    changepassword();
                }

            }
            private void changepassword(){
                FirebaseAuth auth= FirebaseAuth.getInstance();
                emailString=emailEditText.getText().toString();
                auth.sendPasswordResetEmail(emailString).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(StaffResetPassword.this,"Check your email",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(StaffResetPassword.this, Login.class));
                            finish();
                        }else{
                            Toast.makeText(StaffResetPassword.this,"Error :"+ task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }

        });
    }


}