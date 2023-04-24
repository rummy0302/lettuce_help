package com.example.loginregisterfirebase.Volunteers.NavBar;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginregisterfirebase.Volunteers.VolunteerHomePage;
import com.example.loginregisterfirebase.Volunteers.VolunteerMaps;
import com.example.loginregisterfirebase.Volunteers.VolunteerSettings;

public class VolunteerNavBar extends  AppCompatActivity implements NavBarListener {


    @Override
    public void onMapButtonClick(Button button,Context context) {


        if (context instanceof VolunteerMaps){
            Toast.makeText(context,"You are in the Maps page",Toast.LENGTH_SHORT).show();
        }else{
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VolunteerMaps.class);
                    context.startActivity(intent);
                }
            });
    }
    }

    @Override
    public void onHomeButtonClick(Button button,Context context) {

        if (context instanceof VolunteerHomePage){
            Toast.makeText(context,"You are in the Homepage",Toast.LENGTH_SHORT).show();
        }else{
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VolunteerHomePage.class);
                context.startActivity(intent);
            }
        });
    }}

    @Override
    public void onSettingsButtonClick(Button button,Context context) {

        if (context instanceof VolunteerSettings){
            Toast.makeText(context,"You are in the Settings page",Toast.LENGTH_SHORT).show();
        }
        else{
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VolunteerSettings.class);
                context.startActivity(intent);
            }
        });


    }
}}
