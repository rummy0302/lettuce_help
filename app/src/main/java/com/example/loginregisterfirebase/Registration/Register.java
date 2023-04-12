package com.example.loginregisterfirebase.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregisterfirebase.Login;
import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Validator.ContactNumberValidator;
import com.example.loginregisterfirebase.Validator.EmailValidator;
import com.example.loginregisterfirebase.Validator.NameValidator;
import com.example.loginregisterfirebase.Validator.PasswordValidator;
import com.example.loginregisterfirebase.Validator.UsertypeValidator;
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

public class Register extends AppCompatActivity implements NameValidator,EmailValidator, PasswordValidator, UsertypeValidator, ContactNumberValidator {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText fullname, email, contactnumber, password, conpassword;
        RadioGroup VolunteerOrStaffRadioGroup;



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

        // Returns identifer of the selected radio button in the radio group.
        // If volunteer is selected, radio_volunteer is saved as selectedusertype
        // Upon empty selection, returned value is -1
        int selectedusertype = VolunteerOrStaffRadioGroup.getCheckedRadioButtonId();




        //Register Button
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Firebase Authenticator
                FirebaseAuth auth = FirebaseAuth.getInstance();

                //Firebase Realtime Database
                DatabaseReference DBR = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-2f629-default-rtdb.firebaseio.com/");

                //Validation
                if (ValidateName(fullname) && ValidateEmail(email) && ValidateContactNumber(contactnumber)&& ValidatePassword(password) && ValidatePassword(conpassword)
                && ValidateUser(VolunteerOrStaffRadioGroup) )
                {
                    RegisterUser(fullname, email, contactnumber, password,selectedusertype, auth, DBR);}
                }
            });
        }

    private void RegisterUser(EditText fullname, EditText email, EditText contactnumber, EditText password, int selectedusertype, FirebaseAuth auth, DatabaseReference DBR) {
        auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Register.this, "Registration Successful. Please Login", Toast.LENGTH_LONG).show();

                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    Registration_Fields user = new Registration_Fields(fullname.getText().toString() , email.getText().toString(), contactnumber.getText().toString());
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


    //============== Validation methods =========//
    @Override
    public Boolean ValidateName(EditText e) {
        if (e.getText().toString().isEmpty()) {
            Toast.makeText(Register.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
            e.setError("Full name is required");
            e.requestFocusFromTouch();
            return false;
        } else {
            return true;
            }
    }
    @Override
    public Boolean ValidatePassword(EditText e) {
        if (e.getText().toString().isEmpty()) {
            Toast.makeText(Register.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            e.setError("Password is required");
            e.requestFocus();
            return false;
        }
        else if (e.getText().toString().length() < 8 || e.getText().toString().length() > 12) {
            Toast.makeText(Register.this, "Password should be 8 to 12 characters long", Toast.LENGTH_SHORT).show();
            e.setError("8-12 character password required");
            e.requestFocus();
            e.clearComposingText();
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public Boolean ValidateEmail(EditText e) {
        if (e.getText().toString().isEmpty()) {
            Toast.makeText(Register.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            e.setError("Email is required");
            e.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(e.getText().toString()).matches()) {
            Toast.makeText(Register.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
            e.setError("Valid email is required");
            e.requestFocus();
            return false;
        }else {
            return true;
        }
    }
    @Override
    public Boolean ValidateUser(RadioGroup RG) {
        if (RG.getCheckedRadioButtonId()==-1 ) {
                Toast.makeText(Register.this, "Please select volunteer or staff", Toast.LENGTH_SHORT).show();
                RG.requestFocus();
                return false;
            }
        else {
            return true;
        }
    }

    @Override
    public Boolean ValidateContactNumber(EditText e) {
            if ((e.getText().toString().isEmpty())){
                Toast.makeText(Register.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
                e.setError("Mobile number is required");
                e.requestFocus();
                return false;
            } else if((e.getText().toString().length() != 8)){
                Toast.makeText(Register.this, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
                e.setError("Mobile number should be 8 digits");
                e.requestFocus();
                return false;
            }
            else {
                return true;
            }
    }
}









