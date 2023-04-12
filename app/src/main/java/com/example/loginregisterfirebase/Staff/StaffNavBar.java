package com.example.loginregisterfirebase.Staff;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView.StaffHomepage;
//
//public class StaffNavBar {
//    //========= NAV BAR =========//
//    final Button mapStaffBtn = findViewById(R.id.mapStaffBtn);
//    final Button boxStaffBtn= findViewById(R.id.boxStaffBtn);
//    final Button settingsStaffBtn=findViewById(R.id.settingsStaffBtn);
//
//        mapStaffBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Uri.Builder uriBuilder = new Uri.Builder();
//            /** geo:0.0?q=ChangiAirport */
//
//            int i = 806748;
//            String s = Integer.toString(i);
//
//            uriBuilder.scheme("geo").opaquePart("0.0")
//                    .appendQueryParameter("q","806748");
//
//            Uri uri = uriBuilder.build();
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(uri);
//            if( intent.resolveActivity(getPackageManager()) != null){
//                startActivity(intent);
//            }
//        }
//    });
//
//        boxStaffBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(StaffHomepage.this,"You are in the homescreen",Toast.LENGTH_SHORT).show();
//        }
//    });
//
//        settingsStaffBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            startActivity(new Intent(StaffHomepage.this, StaffSettings.class));
//        }
//    });
//
//
//}
//}
