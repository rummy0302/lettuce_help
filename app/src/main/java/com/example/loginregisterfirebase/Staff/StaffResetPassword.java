package com.example.loginregisterfirebase.Staff;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginregisterfirebase.Login;
import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView.StaffHomepage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class StaffResetPassword extends AppCompatActivity {

    private EditText emailEditText;
    private Button changePasswordButton,mapStaffBtn,boxStaffBtn,settingStaffBtn;
    private String emailString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_reset_password);

        emailEditText = findViewById(R.id.emailResetPassword) ;
        changePasswordButton = findViewById(R.id.updatePasswordBtn);
        mapStaffBtn=findViewById(R.id.mapBtn_Staff);
        boxStaffBtn=findViewById(R.id.boxStaffBtn);
        settingStaffBtn=findViewById(R.id.settingsBtn_Staff);



        mapStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri.Builder uriBuilder = new Uri.Builder();
                /** geo:0.0?q=ChangiAirport */

                int i = 806748;
                String s = Integer.toString(i);

                uriBuilder.scheme("geo").opaquePart("0.0")
                        .appendQueryParameter("q","806748");

                Uri uri = uriBuilder.build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                if( intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });

        boxStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffResetPassword.this, StaffHomepage.class));
            }
        });

        settingStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffResetPassword.this,StaffSettings.class));
            }
        });


        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailString = emailEditText.getText().toString();
                if(emailString.isEmpty()){
                    Toast.makeText(StaffResetPassword.this,"Please provide an email",Toast.LENGTH_LONG).show();
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