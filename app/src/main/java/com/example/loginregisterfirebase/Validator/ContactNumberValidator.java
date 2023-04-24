package com.example.loginregisterfirebase.Validator;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContactNumberValidator implements Validator{
    @Override
    public Boolean Validate(View view, Context context) {
            if ((((EditText) view).getText().toString().isEmpty())){
                Toast.makeText(context, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
                ((EditText) view).setError("Mobile number is required");
                ((EditText) view).requestFocus();
                return false;
            } else if((((EditText) view).getText().toString().length() != 8)){
                Toast.makeText(context, "Please re-enter your mobile number", Toast.LENGTH_SHORT).show();
                ((EditText) view).setError("Mobile number should be 8 digits");
                ((EditText) view).requestFocus();
                return false;
            }
            else {
                return true;
            }
        }

}
