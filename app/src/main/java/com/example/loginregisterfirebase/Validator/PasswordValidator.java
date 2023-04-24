package com.example.loginregisterfirebase.Validator;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordValidator implements Validator{
    @Override
    public Boolean Validate(View e, Context context) {
            if (((EditText) e).getText().toString().isEmpty()) {
                Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show();
                ((EditText) e).setError("Password is required");
                ((EditText) e).requestFocus();
                return false;
            } else if (((EditText) e).getText().toString().length() < 8 || ((EditText) e).getText().toString().length() > 12) {
                Toast.makeText(context, "Password should be 8 to 12 characters long", Toast.LENGTH_SHORT).show();
                ((EditText) e).setError("8-12 character password required");
                ((EditText) e).requestFocus();
                ((EditText) e).clearComposingText();
                return false;
            } else {
                return true;
            }
        }
    }

