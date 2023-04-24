package com.example.loginregisterfirebase.Staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Report_Fields;
import com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView.StaffHomepage;
import com.example.loginregisterfirebase.Validator.ContactNumberValidator;
import com.example.loginregisterfirebase.Validator.EmailValidator;
import com.example.loginregisterfirebase.Validator.NameValidator;
import com.example.loginregisterfirebase.Validator.ReportFieldValidator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StaffReport extends AppCompatActivity {
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

                //Validator objects
                EmailValidator emailValidator = new EmailValidator();
                NameValidator nameValidator = new NameValidator();
                ContactNumberValidator contactNumberValidator = new ContactNumberValidator();
                ReportFieldValidator reportFieldValidator = new ReportFieldValidator();

                //Validation
                if (nameValidator.Validate(name, StaffReport.this)
                        && emailValidator.Validate(email, StaffReport.this)
                        && contactNumberValidator.Validate(phone, StaffReport.this)
                        && reportFieldValidator.Validate(details, StaffReport.this)) {

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

        });
    }
}



