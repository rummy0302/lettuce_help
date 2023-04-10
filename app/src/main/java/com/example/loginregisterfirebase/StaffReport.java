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


    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    private Long longTreeSize;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_report);

        name = findViewById(R.id.report_name);
        email = findViewById(R.id.report_email);
        phone = findViewById(R.id.contact_number);
        details = findViewById(R.id.details_content);
        submitBtn = findViewById(R.id.submitBtn);


        DatabaseReference DBR = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-2f629-default-rtdb.firebaseio.com/").child("Report");


        submitBtn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             try {
                                                 if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                                                     startActivity(new Intent(StaffReport.this, Login.class));
                                                     // finish makes the back button quit the app
//                        finish();
                                                 }


                                                 //Obtain entered data
                                                 final String nameStr = name.getText().toString();
                                                 final String emailStr = email.getText().toString();
                                                 final String contactNumberStr = phone.getText().toString();
                                                 final String issueStr = details.getText().toString();

                                                 //TODO: Fix this button
                                                 //Send report
                                                 Report_Fields report = new Report_Fields(nameStr, emailStr, contactNumberStr, issueStr);
                                                 DBR.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(report);

                                             } catch (NullPointerException e) {
                                                 Toast.makeText(StaffReport.this, "Please fill in all the fields!", Toast.LENGTH_SHORT).show();
                                             }}
        });


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





//            private void sendIssue(String nameStr, String emailStr, String contactnumberStr, String issueStr) {
//                String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
//
//                DB = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-2f629-default-rtdb.firebaseio.com/");
//                DB.child("Report").child().addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            startActivity(new Intent(VolunteerReport.this, StaffHomeage.class));
//                            finish();
//                            Toast.makeText(VolunteerReport.this, "You have successfully sent ", Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "Send report: success");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//
//                });
//            }

    }
}