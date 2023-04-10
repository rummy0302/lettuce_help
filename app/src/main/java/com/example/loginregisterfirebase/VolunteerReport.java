package com.example.loginregisterfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class VolunteerReport extends AppCompatActivity {
    private EditText name, email, phone, issue;
    private DatabaseReference DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_report);
        name = findViewById(R.id.report_name);
        email = findViewById(R.id.report_email);
        phone = findViewById(R.id.contact_number);
        issue = findViewById(R.id.issue_content);
        final Button submit_issue = findViewById(R.id.submit_issue);
        submit_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        startActivity(new Intent(VolunteerReport.this, Login.class));
                        // finish makes the back button quit the app
//                        finish();
                    }
                    //Obtain entered data
                    final String nameStr = name.getText().toString();
                    final String emailStr = email.getText().toString();
                    final String contactNumberStr = phone.getText().toString();
                    final String issueStr = issue.getText().toString();
//                    sendIssue(nameStr, emailStr, contactNumberStr, issueStr);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseLink = database.getReference("Report");
                    String key = databaseLink.push().getKey();
                    Report report = new Report(nameStr, emailStr, contactNumberStr, issueStr);

                    databaseLink.child(key).setValue(report);

                } catch (NullPointerException e) {
                    Toast.makeText(VolunteerReport.this, "Please fill in all the fields!", Toast.LENGTH_SHORT).show();
                }
            }

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
        });
    }
}