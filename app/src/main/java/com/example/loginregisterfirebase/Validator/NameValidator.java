package com.example.loginregisterfirebase.Validator;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class NameValidator implements Validator{
    @Override
    public Boolean Validate(View e, Context context) {
            if (((EditText) e).getText().toString().isEmpty()) {
                Toast.makeText(context, "Please enter your full name", Toast.LENGTH_SHORT).show();
                ((EditText) e).setError("Full name is required");
                ((EditText) e).requestFocusFromTouch();
                return false;
            } else {
                return true;
            }
        }
    }

