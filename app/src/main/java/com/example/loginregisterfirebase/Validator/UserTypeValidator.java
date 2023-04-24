package com.example.loginregisterfirebase.Validator;

import android.content.Context;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserTypeValidator implements Validator{
    @Override
    public Boolean Validate(View e, Context context) {
            if (((RadioGroup) e).getCheckedRadioButtonId()==-1 ) {
                Toast.makeText(context, "Please select volunteer or staff", Toast.LENGTH_SHORT).show();
                ((RadioGroup) e).requestFocus();
                return false;
            }
            else {
                return true;
            }
        }
    }

