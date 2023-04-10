package com.example.loginregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private EditText fullname, email, contactnumber, password, conpassword;
    private RadioGroup VolunteerOrStaffRadioGroup;
    private RadioButton VolunteerOrStaffselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // EditText
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        contactnumber = findViewById(R.id.ContactNumber);
        password = findViewById(R.id.password);
        conpassword = findViewById(R.id.conpassword);


        //RadioButton for User type: Volunteer or Staff
        VolunteerOrStaffRadioGroup = findViewById(R.id.Radio_Group_VS);
        VolunteerOrStaffRadioGroup.clearCheck(); //clearing all checked radio buttons when activity is started or resumed

        //RegisterBtn defined locally because its only used in this activity
        Button registerBtn = findViewById(R.id.registerBtn);

        //LoginBtn defined locally because its only used in this activity
        TextView loginNowBtn = findViewById(R.id.loginNow);


        //LoginNow Button
        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish(); //to close Register activity
            }
        });

        //Register Button
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Returns identifer of the selected radio button in the radio group.
                // If volunteer is selected, radio_volunteer is saved as selectedusertype
                // Upon empty selection, returned value is -1
                int selectedusertype = VolunteerOrStaffRadioGroup.getCheckedRadioButtonId();
                VolunteerOrStaffselected = findViewById(selectedusertype);


                //Obtain entered data
                final String fullnameTxt = fullname.getText().toString();
                final String emailTxt = email.getText().toString();
                final String contactnumberTxt = contactnumber.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conpasswordTxt = conpassword.getText().toString();

                // VSTxt is a variable for the radio group
                // Trying to extract the value from an unselected radio group results in a NullPointException
                // Therefore we must verify if any button was selected first.
                final String VSTxt;

                //Firebase Authenticator
                FirebaseAuth auth = FirebaseAuth.getInstance();

                //Firebase Realtime Database
                DatabaseReference DBR = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-2f629-default-rtdb.firebaseio.com/");


                //Checking for valid fields
                //TextUtils returns true iff length() = 0
                if (TextUtils.isEmpty(fullnameTxt)) {
                    Toast.makeText(Register.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
                    fullname.setError("Full name is required");
                    fullname.requestFocus(); // .requestFocus() used to display error message
                } else if (TextUtils.isEmpty(emailTxt)) {
                    Toast.makeText(Register.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    email.setError("Email is required");
                    email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches())  // Pattern matching, formally known as matching a regular expression(pattern) against a text using JAVA
                {
                    Toast.makeText(Register.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    email.setError("Valid email is required");
                    email.requestFocus();
                } else if (VolunteerOrStaffRadioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(Register.this, "Please select volunteer or staff", Toast.LENGTH_SHORT).show();
                    VolunteerOrStaffselected.setError("Required to select volunteer or staff");
                    VolunteerOrStaffselected.requestFocus();
                } else if (TextUtils.isEmpty(contactnumberTxt)) {
                    Toast.makeText(Register.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
                    contactnumber.setError("Mobile number is required");
                    contactnumber.requestFocus();
                } else if (contactnumberTxt.length() != 8) {
                    Toast.makeText(Register.this, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
                    contactnumber.setError("Mobile number should be 8 digits");
                    contactnumber.requestFocus();
                } else if (TextUtils.isEmpty(passwordTxt)) {
                    Toast.makeText(Register.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    password.setError("Password is required");
                    password.requestFocus();
                } else if (passwordTxt.length() < 8 || passwordTxt.length() > 12) {
                    Toast.makeText(Register.this, "Password should be 8 to 12 characters long", Toast.LENGTH_SHORT).show();
                    password.setError("8-12 character password required");
                    password.requestFocus();
                } else if (TextUtils.isEmpty(conpasswordTxt)) {
                    Toast.makeText(Register.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                    conpassword.setError("Password confirmation is required");
                    conpassword.requestFocus();
                } else if (!passwordTxt.equals(conpasswordTxt)) {
                    Toast.makeText(Register.this, "Please enter the same password", Toast.LENGTH_SHORT).show();
                    conpassword.setError("Password confirmation is required");
                    conpassword.requestFocus();
                    //clear the entered password
                    password.clearComposingText();
                    conpassword.clearComposingText();
                } else {

                    RegisterUser(fullnameTxt, emailTxt, contactnumberTxt, passwordTxt,selectedusertype, auth, DBR);

                }


            }
        });
    }

    private void RegisterUser(String fullname, String email, String contactnumber, String password, int selectedusertype, FirebaseAuth auth, DatabaseReference DBR) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Register.this, "Email Verification Sent", Toast.LENGTH_LONG).show();


                    // Send Verification email
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    firebaseUser.sendEmailVerification();


                    //Upload data to Realtime Database
                    Registration_Fields user = new Registration_Fields(fullname, email, contactnumber,selectedusertype);
                    String userid= firebaseUser.getUid();
                    DBR.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild("user")) {
                                Toast.makeText(Register.this, "User is already registered", Toast.LENGTH_SHORT).show();
                            } else {

                                if (selectedusertype == R.id.Radio_Staff) {

                                    DBR.child("Staff").child(userid).setValue(user);
                                } else {
                                    DBR.child("Volunteers").child(userid).setValue(user);
                                }
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    //Proceed back to login page
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                    finish(); //to close Register activity
                } else //exception handling: required for email authentication

                {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthUserCollisionException e) {
                        Toast.makeText(Register.this, "Email already in use, Please try again", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}









