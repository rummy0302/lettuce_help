package com.example.loginregisterfirebase.Staff;

import android.content.Intent;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.loginregisterfirebase.R;

import com.example.loginregisterfirebase.Report_Fields;
import com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView.StaffHomepage;
import com.example.loginregisterfirebase.Validator.ContactNumberValidator;
import com.example.loginregisterfirebase.Validator.EmailValidator;
import com.example.loginregisterfirebase.Validator.NameValidator;
import com.example.loginregisterfirebase.Validator.ReportValidator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StaffReport extends AppCompatActivity implements NameValidator, EmailValidator, ContactNumberValidator, ReportValidator {
    private EditText name, email, phone, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_report);
        name = findViewById(R.id.report_name);
        email = findViewById(R.id.report_email);
        phone = findViewById(R.id.contact_number);
        details = findViewById(R.id.details_content);
        final Button submit_issue = findViewById(R.id.submitBtn);
        submit_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validation
                if (ValidateName(name) && ValidateEmail(email) && ValidateContactNumber(phone) && ValidateReport(details)) {

                    String nameStr = name.getText().toString();
                    String emailStr = email.getText().toString();
                    String contactNumberStr = phone.getText().toString();
                    String detailsStr = details.getText().toString();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseLink = database.getReference("Report");
                    String key = databaseLink.push().getKey();
                    Report_Fields report = new Report_Fields(nameStr, emailStr, contactNumberStr, detailsStr);

                    databaseLink.child(key).setValue(report);
                    Toast.makeText(StaffReport.this, "Your report has been made! Our team will contact you in 3 working days.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StaffReport.this, StaffHomepage.class));
                }
            }

            ;

        });
    }
    @Override
    public Boolean ValidateName(EditText e) {
        if (e.getText().toString().isEmpty()) {
            Toast.makeText(StaffReport.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
            e.setError("Full name is required");
            e.requestFocusFromTouch();
            return false;
        } else {
            return true;
        }
    }


    @Override
    public Boolean ValidateEmail(EditText e) {
        if (e.getText().toString().isEmpty()) {
            Toast.makeText(StaffReport.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            e.setError("Email is required");
            e.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(e.getText().toString()).matches()) {
            Toast.makeText(StaffReport.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
            e.setError("Valid email is required");
            e.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    @Override
    public Boolean ValidateContactNumber(EditText e) {
        if ((e.getText().toString().isEmpty())){
            Toast.makeText(StaffReport.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
            e.setError("Mobile number is required");
            e.requestFocus();
            return false;
        } else if((e.getText().toString().length() != 8)){
            Toast.makeText(StaffReport.this, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
            e.setError("Mobile number should be 8 digits");
            e.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public Boolean ValidateReport(EditText e) {
        if  (e.getText().toString().isEmpty()) {
            Toast.makeText(StaffReport.this, "Please describe the problem", Toast.LENGTH_SHORT).show();
            e.setError("Please describe the problem ");
            e.requestFocus();
            return false;
        }else{return true;}


}}



