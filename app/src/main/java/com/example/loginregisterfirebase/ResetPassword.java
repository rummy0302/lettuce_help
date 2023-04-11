package com.example.loginregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText emailEditText;
    private Button changePasswordButton;

    private String emailString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailEditText = findViewById(R.id.emailResetPassword) ;
        changePasswordButton = findViewById(R.id.updatePasswordBtn);


        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailString = emailEditText.getText().toString();
                if(emailString.isEmpty()){
                    Toast.makeText(ResetPassword.this,"Please provide an email",Toast.LENGTH_LONG).show();
                    emailEditText.setError("Password confirmation is required");
                    emailEditText.requestFocus();
                }
                else{
                    changepassword();
                }
            }
            private void changepassword(){
                FirebaseAuth auth= FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(emailString).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ResetPassword.this,"Check your email",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetPassword.this, Login.class));
                            finish();
                        }else{
                            Toast.makeText(ResetPassword.this,"Error :"+ task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }

        });
    }
}