package com.example.loginregisterfirebase.Volunteers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginregisterfirebase.Login;
import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Report_Fields;
import com.example.loginregisterfirebase.Staff.StaffReport;
import com.example.loginregisterfirebase.Validator.ContactNumberValidator;
import com.example.loginregisterfirebase.Validator.EmailValidator;
import com.example.loginregisterfirebase.Validator.NameValidator;
import com.example.loginregisterfirebase.Validator.ReportValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VolunteerReport extends AppCompatActivity implements NameValidator, EmailValidator, ContactNumberValidator, ReportValidator {
    private EditText name, email, phone, details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_report);
        name = findViewById(R.id.report_name);
        email = findViewById(R.id.report_email);
        phone = findViewById(R.id.contact_number);
        details = findViewById(R.id.details_content);
        final Button submit_issue = findViewById(R.id.submitBtn);
        submit_issue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //Obtain entered data
                final String nameStr = name.getText().toString();
                final String emailStr = email.getText().toString();
                final String contactNumberStr = phone.getText().toString();
                final String detailsStr = details.getText().toString();

                //check for empty editTexts, all must be filled
                if (ValidateName(name) && ValidateEmail(email) && ValidateContactNumber(phone) && ValidateReport(details)) {
                    // upload data
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseLink = database.getReference("Report");
                    String key = databaseLink.push().getKey();
                    Report_Fields report = new Report_Fields(nameStr, emailStr, contactNumberStr, detailsStr);
                    databaseLink.child(key).setValue(report);
                    Toast.makeText(VolunteerReport.this, "Your report has been made! Our team will contact you in 3 working days.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(VolunteerReport.this, VolunteerHomePage.class));


                }

            }
        });

        //==================== NAV BAR  ================//
        Button mapbtn = findViewById(R.id.mapBtn_Volunteer);
        Button homebtn=findViewById(R.id.homeBtn_Volunteer);
        Button settingsbtn=findViewById(R.id.settingsBtn_Volunteer);

        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VolunteerReport.this, VolunteerMaps.class));
            }
        });

        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VolunteerReport.this,VolunteerSettings.class));
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VolunteerReport.this, VolunteerHomePage.class));

            }
        });

    }
    @Override
    public Boolean ValidateName(EditText e) {
        if (e.getText().toString().isEmpty()) {
            Toast.makeText(VolunteerReport.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(VolunteerReport.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            e.setError("Email is required");
            e.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(e.getText().toString()).matches()) {
            Toast.makeText(VolunteerReport.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(VolunteerReport.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
            e.setError("Mobile number is required");
            e.requestFocus();
            return false;
        } else if((e.getText().toString().length() != 8)){
            Toast.makeText(VolunteerReport.this, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(VolunteerReport.this, "Please describe the problem", Toast.LENGTH_SHORT).show();
            e.setError("Please describe the problem ");
            e.requestFocus();
            return false;
        }else{return true;}


    }
}