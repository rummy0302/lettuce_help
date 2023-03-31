package com.example.loginregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-2f629-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullname = findViewById(R.id.fullname);
        final EditText email = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.userid);
        final EditText password = findViewById(R.id.password);
        final EditText conPassword = findViewById(R.id.conpassword);
        final Switch staffquery= findViewById(R.id.staffquery);

        final Button registerBtn = findViewById(R.id.registerBtn);
        final TextView loginNowBtn = findViewById(R.id.loginNow );

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertUserData(fullname,email,phone,password,conPassword,staffquery);
            }
        });

        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    private void InsertUserData(EditText fullname,EditText email,EditText phone,EditText password,EditText conPassword,Switch staffquery){

        // get data fron EditTexts into String variables
        final String fullnameTxt = fullname.getText().toString();
        final String emailTxt = email.getText().toString();
        final String phoneTxt = phone.getText().toString();
        final String passwordTxt = password.getText().toString();
        final String conPsswordTxt = conPassword.getText().toString();



        //check if user fill all the fields before sending data to firebase
        if(fullnameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passwordTxt.isEmpty()){
            Toast.makeText(Register.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
        }

        // check if passwords are matching with each other
        // if not matching with each other then show a toast
        else if (!passwordTxt.equals(conPsswordTxt)){
            Toast.makeText(Register.this,"Passwords do not match", Toast.LENGTH_SHORT).show();
        }

        else{

            User user= new User(fullnameTxt,emailTxt,phoneTxt,passwordTxt,conPsswordTxt,"Volunteer");

            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild("user")) {
                        Toast.makeText(Register.this, "User is already registered", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        if (staffquery.isChecked()){
                            User userstaff= new User(fullnameTxt,emailTxt,phoneTxt,passwordTxt,conPsswordTxt,"Staff");
                            databaseReference.push().setValue(userstaff);
                        }
                        else{
                            databaseReference.push().setValue(user);
                        }
                        Toast.makeText(Register.this,"User registered successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }


}