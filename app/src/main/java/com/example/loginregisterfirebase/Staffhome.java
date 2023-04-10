package com.example.loginregisterfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Staffhome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffhome);

        final Button mapstaffbtn = findViewById(R.id.mapstaffbtn);

        mapstaffbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri.Builder uriBuilder = new Uri.Builder();
                /** geo:0.0?q=ChangiAirport */

                uriBuilder.scheme("geo").opaquePart("0.0")
                        .appendQueryParameter("q","ChangiAirport");

                Uri uri = uriBuilder.build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                if( intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });




    }
}