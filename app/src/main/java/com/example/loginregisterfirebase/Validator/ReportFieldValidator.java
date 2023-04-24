package com.example.loginregisterfirebase.Validator;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ReportFieldValidator implements Validator{
    @Override
    public Boolean Validate(View e, Context context) {

            if  (((EditText) e).getText().toString().isEmpty()) {
                Toast.makeText(context, "Please describe the problem", Toast.LENGTH_SHORT).show();
                ((EditText) e).setError("Please describe the problem ");
                ((EditText) e).requestFocus();
                return false;
            }else{return true;}


        }
}
