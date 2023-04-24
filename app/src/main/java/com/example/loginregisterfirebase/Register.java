package com.example.loginregisterfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginregisterfirebase.Validator.ContactNumberValidator;
import com.example.loginregisterfirebase.Validator.EmailValidator;
import com.example.loginregisterfirebase.Validator.NameValidator;
import com.example.loginregisterfirebase.Validator.PasswordValidator;
import com.example.loginregisterfirebase.Validator.UserTypeValidator;
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

                //Firebase Authenticator instance
                FirebaseAuth auth = FirebaseAuth.getInstance();

                //Firebase Realtime Database instance
                DatabaseReference DBR = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-2f629-default-rtdb.firebaseio.com/");

                //Validation Objects
                EmailValidator emailValidator= new EmailValidator();
                NameValidator nameValidator = new NameValidator();
                ContactNumberValidator contactNumberValidator=new ContactNumberValidator();
                PasswordValidator passwordValidator= new PasswordValidator();
                UserTypeValidator userTypeValidator=new UserTypeValidator();

                //Validate
                if (nameValidator.Validate(fullname,Register.this)
                        && emailValidator.Validate(email,Register.this)
                        && contactNumberValidator.Validate(contactnumber,Register.this)
                        && passwordValidator.Validate(password, Register.this)
                        && passwordValidator.Validate(conpassword,Register.this)
                        && userTypeValidator.Validate(VolunteerOrStaffRadioGroup,Register.this)) {
                    RegisterUser(fullname, email, contactnumber, password, selectedusertype, auth, DBR);
                }
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


}









