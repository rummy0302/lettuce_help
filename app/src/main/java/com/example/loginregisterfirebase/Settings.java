//package com.example.loginregisterfirebase;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class Settings extends AppCompatActivity {
//
//    private EditText mOldPasswordEditText;
//    private EditText mNewPasswordEditText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
//
//        mOldPasswordEditText = findViewById(R.id.old_password_edit_text);
//        mNewPasswordEditText = findViewById(R.id.new_password_edit_text);
//
//        Button changePasswordButton = findViewById(R.id.change_password_button);
//        changePasswordButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changePassword();
//            }
//        });
//    }
//
//    private void changePassword() {
//        String oldPassword = mOldPasswordEditText.getText().toString();
//        String newPassword = mNewPasswordEditText.getText().toString();
//
//        // TODO: validate old and new passwords
//         DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-2f629-default-rtdb.firebaseio.com/");
//
//        if (user != null) {
//            // reauthenticate the user with their old password
//            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
//            user.reauthenticate(credential)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            // update the password
//                            user.updatePassword(newPassword)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            // password updated successfully
//                                            Toast.makeText(getApplicationContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
//                                            // TODO: update password in database
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            // password update failed
//                                            Toast.makeText(getApplicationContext(), "Failed to update password", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            // authentication failed
//                            Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
//    }
//
//}
