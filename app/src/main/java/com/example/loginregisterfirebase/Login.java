package com.example.loginregisterfirebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView.StaffHomepage;
import com.example.loginregisterfirebase.Staff.StaffResetPassword;
import com.example.loginregisterfirebase.Validator.EmailValidator;
import com.example.loginregisterfirebase.Validator.PasswordValidator;
import com.example.loginregisterfirebase.Volunteers.VolunteerHomePage;
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

public class  Login extends AppCompatActivity implements EmailValidator, PasswordValidator {

    private EditText userid, password;
    private FirebaseAuth authProfile;
    private DatabaseReference DB;
    public static final String SHARED_PREFS = "shared_prefs";
    // key for storing email.
    public static final String EMAIL_KEY = "email_key";
    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userid = findViewById(R.id.userid);
        password = findViewById(R.id.password);

        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final TextView forgotpwdBtn = findViewById(R.id.forgotPassword);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        authProfile = FirebaseAuth.getInstance();
        DB = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-2f629-default-rtdb.firebaseio.com/");


        forgotpwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, StaffResetPassword.class));
            }
        });
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

                //Validation
                if (ValidateEmail(userid) && ValidatePassword(password)) {
                    loginUser(userid.getText().toString(), password.getText().toString());
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

                    if (user != null) {
                        //checking shared preferences file
                        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences(SHARED_PREFS, android.content.Context.MODE_PRIVATE);
                        String s1 = sharedpreferences.getString(EMAIL_KEY, "");
                        String a = sharedpreferences.getString(PASSWORD_KEY, "");


                        //Find out if user is a staff or volunteer
                        String U_Id = user.getUid();

                        //Check U_Id in DB "Staff"
                        DB.child("Staff").child(U_Id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    if (s1.isEmpty() && a.isEmpty()) {
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(EMAIL_KEY, userTxt);
                                        editor.putString(PASSWORD_KEY, passwordTxt);
                                        editor.apply();
                                    }
                                    startActivity(new Intent(Login.this, StaffHomepage.class));
                                    finish();
                                    Toast.makeText(Login.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "SignInWithEmail:staff success");
                                } else {
                                    //Check U_Id in DB "Volunteer"
                                    DB.child("Volunteers").child(U_Id).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                // Setting the credentials data in the shared preferences
                                                if (s1.isEmpty() && a.isEmpty()) {
                                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                                    editor.putString(EMAIL_KEY, userTxt);
                                                    editor.putString(PASSWORD_KEY, passwordTxt);
                                                    editor.apply();
                                                }
                                                startActivity(new Intent(Login.this, VolunteerHomePage.class));
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


                } else {
                    //The user is not found in Staff nor Volunteer
                    //User does not exist or has incorrectly entered an Id/Password that is not found in auth
                    userid.setError("Re-enter your email");
                    userid.requestFocus();
                    password.setError("Re-enter password");
                    password.requestFocus();
                    Toast.makeText(Login.this, "User not found. Please re-enter your email and password.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "No Uid found in Firebase Auth");
                }
            }
        });
    }

    //     Fetch the stored data in onResume() Because this is what will be called when the app opens again
    @Override
    protected void onResume() {
        super.onResume();
        // Fetching the stored data from the SharedPreference
        SharedPreferences sh = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String s1 = sh.getString(EMAIL_KEY, "");
        String a = sh.getString(PASSWORD_KEY, "");

        // Setting the fetched data in the EditTexts
        if (!(s1.isEmpty() && a.isEmpty())) {
            loginUser(s1, a);
        }
    }

    @Override
    public Boolean ValidatePassword(EditText e) {
        if (e.getText().toString().isEmpty()) {
            Toast.makeText(Login.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            e.setError("Password is required");
            e.requestFocus();
            return false;
        } else if (e.getText().toString().length() < 8 || e.getText().toString().length() > 12) {
            Toast.makeText(Login.this, "Password should be 8 to 12 characters long", Toast.LENGTH_SHORT).show();
            e.setError("8-12 character password required");
            e.requestFocus();
            e.clearComposingText();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean ValidateEmail(EditText e) {
        if (e.getText().toString().isEmpty()) {
            Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            e.setError("Email is required");
            e.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(e.getText().toString()).matches()) {
            Toast.makeText(Login.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
            e.setError("Valid email is required");
            e.requestFocus();
            return false;
        } else {
            return true;
        }
    }
}



