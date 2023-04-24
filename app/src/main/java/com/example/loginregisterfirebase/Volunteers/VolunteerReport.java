package com.example.loginregisterfirebase.Volunteers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Report_Fields;
import com.example.loginregisterfirebase.Validator.ContactNumberValidator;
import com.example.loginregisterfirebase.Validator.EmailValidator;
import com.example.loginregisterfirebase.Validator.NameValidator;
import com.example.loginregisterfirebase.Validator.ReportFieldValidator;
import com.example.loginregisterfirebase.Validator.Validator;
import com.example.loginregisterfirebase.Volunteers.NavBar.NavBarListener;
import com.example.loginregisterfirebase.Volunteers.NavBar.VolunteerNavBar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VolunteerReport extends AppCompatActivity {
    private EditText nameEditText, emailEditText, phoneEditText, detailsEditText;
    private Validator nameValidator, emailValidator, reportFieldValidator, contactNumberValidator;

    private Button mapbtn,homebtn,settingsbtn;

    private NavBarListener navBar;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_report);


        nameEditText = findViewById(R.id.report_name);
        emailEditText = findViewById(R.id.report_email);
        phoneEditText = findViewById(R.id.contact_number);
        detailsEditText = findViewById(R.id.details_content);

        //Validation Objects
        emailValidator = new EmailValidator();
        nameValidator = new NameValidator();
        reportFieldValidator = new ReportFieldValidator();
        contactNumberValidator = new ContactNumberValidator();

        mapbtn = findViewById(R.id.mapBtn_Volunteer);
        homebtn=findViewById(R.id.homeBtn_Volunteer);
        settingsbtn=findViewById(R.id.settingsBtn_Volunteer);

        //Database
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Report");

        //SubmitButton
        setupSubmitButton();

        //NavBar
        navBar = new VolunteerNavBar();
        navBar.onHomeButtonClick(homebtn,VolunteerReport.this);
        navBar.onMapButtonClick(mapbtn,VolunteerReport.this);
        navBar.onSettingsButtonClick(settingsbtn,VolunteerReport.this);

    }

    private void setupSubmitButton() {
        Button submitButton = findViewById(R.id.submitBtn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameStr = nameEditText.getText().toString();
                String emailStr = emailEditText.getText().toString();
                String contactNumberStr = phoneEditText.getText().toString();
                String detailsStr = detailsEditText.getText().toString();

                if (nameValidator.Validate(nameEditText, VolunteerReport.this)
                        && emailValidator.Validate(emailEditText, VolunteerReport.this)
                        && contactNumberValidator.Validate(phoneEditText, VolunteerReport.this)
                        && reportFieldValidator.Validate(detailsEditText, VolunteerReport.this)) {
                    uploadReport(nameStr, emailStr, contactNumberStr, detailsStr);
                }
            }
        });
    }


    private void uploadReport(String nameStr, String emailStr, String contactNumberStr, String detailsStr) {
        String key = databaseReference.push().getKey();
        Report_Fields report = new Report_Fields(nameStr, emailStr, contactNumberStr, detailsStr);
        databaseReference.child(key).setValue(report);
        Toast.makeText(VolunteerReport.this, "Your report has been made! Our team will contact you in 3 working days.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(VolunteerReport.this, VolunteerHomePage.class));
    }

}

