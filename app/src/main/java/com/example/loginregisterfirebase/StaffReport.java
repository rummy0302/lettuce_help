package com.example.loginregisterfirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        submit_issue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                try {
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        startActivity(new Intent(StaffReport.this, Login.class));
                    }
                    //Obtain entered data
                    final String nameStr = name.getText().toString();
                    final String emailStr = email.getText().toString();
                    final String contactNumberStr = phone.getText().toString();
                    final String detailsStr = details.getText().toString();

                    //check for empty editTexts, all must be filled
                    if (nameStr.isEmpty()) {
                        Toast.makeText(StaffReport.this, "Please enter your name!", Toast.LENGTH_SHORT).show();
                        name.setError("Name is required");
                        name.requestFocus(); }

                    if (emailStr.isEmpty()) {
                        Toast.makeText(StaffReport.this, "Please enter your email!", Toast.LENGTH_SHORT).show();
                        email.setError("Email is required");
                        email.requestFocus(); }

                    if (contactNumberStr.isEmpty()) {
                        Toast.makeText(StaffReport.this, "Please enter your contact number!", Toast.LENGTH_SHORT).show();
                        phone.setError("Phone is required");
                        phone.requestFocus(); }

                    if (detailsStr.isEmpty()) {
                        Toast.makeText(StaffReport.this, "Please enter your issue!", Toast.LENGTH_SHORT).show();
                        details.setError("Issue cannot be empty");
                        details.requestFocus(); }

                    // upload data
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseLink = database.getReference("Report");
                    String key = databaseLink.push().getKey();
                    Report_Fields report = new Report_Fields(nameStr, emailStr, contactNumberStr, detailsStr);

                    databaseLink.child(key).setValue(report);
                    Toast.makeText(StaffReport.this, "Your report has been made! Our team will contact you in 3 working days.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StaffReport.this,StaffHomepage.class));


                } catch (NullPointerException e) {
                    Toast.makeText(StaffReport.this, "Please fill in all the fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //=========== NAV BAR ==========//
        Button mapBtn = findViewById(R.id.staffMapBtn);
        Button homebtn=findViewById(R.id.staffHomeBtn);
        Button settingsbtn=findViewById(R.id.staffSettingsBtn);

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri.Builder uriBuilder = new Uri.Builder();
                /** geo:0.0?q=ChangiAirport */

                uriBuilder.scheme("geo").opaquePart("0.0")
                        .appendQueryParameter("q","ChangiAirport");

                Uri uri = uriBuilder.build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                if( intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });

        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open Register activity
                startActivity(new Intent(StaffReport.this,StaffSettings.class));
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffReport.this,StaffHomepage.class));

            }
        });
    }
}