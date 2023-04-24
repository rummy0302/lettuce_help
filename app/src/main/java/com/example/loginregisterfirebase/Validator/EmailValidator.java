package com.example.loginregisterfirebase.Validator;

import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EmailValidator implements Validator {
    @Override
    public Boolean Validate(View e, Context context) {
            if (((EditText) e).getText().toString().isEmpty()) {
                Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show();
                ((EditText) e).setError("Email is required");
                e.requestFocus();
                return false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(((EditText) e).getText().toString()).matches()) {
                Toast.makeText(context, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                ((EditText) e).setError("Valid email is required");
                ((EditText) e).requestFocus();
                return false;
            } else {
                return true;
            }
        }

    }




