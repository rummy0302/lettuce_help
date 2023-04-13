//package com.example.loginregisterfirebase;
//
//import android.content.Context;
//import android.util.Patterns;
//import android.widget.EditText;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//public class Validation extends Validation_Fields {
//
//    @Override
//    public Boolean ValidateName(Context context, EditText e) {
//        if (e.getText().toString().isEmpty()) {
//            Toast.makeText(context, "Please enter your full name", Toast.LENGTH_SHORT).show();
//            e.setError("Full name is required");
//            e.requestFocusFromTouch();
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    @Override
//    public Boolean ValidatePassword(Context context, EditText e) {
//        if (e.getText().toString().isEmpty()) {
//            Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show();
//            e.setError("Password is required");
//            e.requestFocus();
//            return false;
//        }
//        else if (e.getText().toString().length() < 8 || e.getText().toString().length() > 12) {
//            Toast.makeText(context, "Password should be 8 to 12 characters long", Toast.LENGTH_SHORT).show();
//            e.setError("8-12 character password required");
//            e.requestFocus();
//            e.clearComposingText();
//            return false;
//        }
//        else {
//            return true;
//        }
//    }
//
//    @Override
//    public Boolean ValidateEmail(Context context, EditText e) {
//        if (e.getText().toString().isEmpty()) {
//            Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show();
//            e.setError("Email is required");
//            e.requestFocus();
//            return false;
//        }
//        else if (!Patterns.EMAIL_ADDRESS.matcher(e.getText().toString()).matches()) {
//            Toast.makeText(context, "Please re-enter your email", Toast.LENGTH_SHORT).show();
//            e.setError("Valid email is required");
//            e.requestFocus();
//            return false;
//        }else {
//            return true;
//        }
//    }
//    @Override
//    public Boolean ValidateUser(Context context, RadioGroup RG) {
//        if (RG.getCheckedRadioButtonId()==-1 ) {
//            Toast.makeText(context, "Please select volunteer or staff", Toast.LENGTH_SHORT).show();
//            RG.requestFocus();
//            return false;
//        }
//        else {
//            return true;
//        }
//    }
//
//    @Override
//    public Boolean ValidateContactNumber(Context context, EditText e) {
//        if ((e.getText().toString().isEmpty())){
//            Toast.makeText(context, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
//            e.setError("Mobile number is required");
//            e.requestFocus();
//            return false;
//        } else if((e.getText().toString().length() != 8)){
//            Toast.makeText(context, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
//            e.setError("Mobile number should be 8 digits");
//            e.requestFocus();
//            return false;
//        }
//        else {
//            return true;
//        }
//    }
//}
//
//FirebaseAuth.getInstance().signOut();
//Intent intent = new Intent(Register.this, Login.class);
//startActivity(intent);
//finish();