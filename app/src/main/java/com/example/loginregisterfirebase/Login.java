package com.example.loginregisterfirebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class  Login extends AppCompatActivity {

    private EditText userid, password;
    private FirebaseAuth authProfile;
    private DatabaseReference DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userid = findViewById(R.id.userid);
        password = findViewById(R.id.password);

        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);
        final Button loginBtn = findViewById(R.id.loginBtn);


        //Settings //
        final Button SB = findViewById(R.id.SBTN);

        SB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Settings.class));
                finish();

            }
        });


        // Bad settings //




        authProfile = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-2f629-default-rtdb.firebaseio.com/");


        //registerNowBtn
        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });

        //loginBtn
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userTxt = userid.getText().toString();
                final String passwordTxt = password.getText().toString();

                if (userTxt.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    userid.setError("Email is required");
                    userid.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(userTxt).matches()) {
                    Toast.makeText(Login.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    userid.setError("Valid Email is required");
                    userid.requestFocus();
                } else if (passwordTxt.isEmpty()) {
                    Toast.makeText(Login.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    password.setError("Password is required");
                    password.requestFocus();
                } else {

                    loginUser(userTxt, passwordTxt);


                }
            }
        });
    }

    private void loginUser(String userTxt, String passwordTxt) {
        authProfile.signInWithEmailAndPassword(userTxt, passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser user = authProfile.getCurrentUser();
                    Log.d(TAG, "See this"+user);

                    if (user != null) {

                        //Find out if user is a staff or volunteer
                        String userId = user.getUid();

                        //Check userId in DB "Staff"
                        DB.child("Staff").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    startActivity(new Intent(Login.this, StaffHomeage.class));
                                    finish();
                                    Toast.makeText(Login.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "SignInWithEmail:staff success");
                                } else {
                                    //Check userId in DB "Volunteer"
                                    DB.child("Volunteers").child(userId).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                startActivity(new Intent(Login.this, VolunteerHome.class));
                                                finish();
                                                Toast.makeText(Login.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, "SignInWithEmail:Volunteer success");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });


                    }

                    // user = Null
                    else {
                        Toast.makeText(Login.this, "User not found, Please register", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "signInWithEmail:failure");
                    }

                }
            }
        });
    }
}


