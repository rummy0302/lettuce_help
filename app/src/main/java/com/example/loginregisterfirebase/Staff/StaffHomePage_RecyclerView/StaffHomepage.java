package com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregisterfirebase.Login;
import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Register;
import com.example.loginregisterfirebase.Report_Fields;
import com.example.loginregisterfirebase.Staff.StaffReport;
import com.example.loginregisterfirebase.Staff.StaffSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;

/****

 * adapter: pass listData into MyAdapter and obtains a layout (ViewHolder used for population)
 * LinearLayoutManager: Implementation of the RecyclerView.LayoutManager interface ,used to manage the layout of items in a RecyclerView
 *
 */
public class StaffHomepage extends AppCompatActivity{


    List<RecyclerViewItems> listArray;
    RecyclerView myRecyclerView;

    FirebaseData FirebaseData= new FirebaseData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_homepage);


        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Staff");
        int myTitleColor = ContextCompat.getColor(this, R.color.my_title_color);
        toolbar.setTitleTextColor(myTitleColor);

        myRecyclerView = findViewById(R.id.myRecycler);
        myRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getApplicationContext());
        myRecyclerView.setLayoutManager(LM);

        FirebaseData.GetDataFirebase(myRecyclerView);


        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference boxesRef = databaseRef.child("Boxes"); // Change this to the appropriate path of your "boxes" field in Firebase

        boxesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    double Status= (((childSnapshot.child("Status").getValue(Integer.class))/25.0)*100.0);

//Notification System for ramita
                    if (Status >70.0){

                        String AlertBoxName= (((childSnapshot.child("Address").getValue(String.class))));
                        System.out.println(AlertBoxName);
                        Toast.makeText(StaffHomepage.this,AlertBoxName+"Box needs to be collected",Toast.LENGTH_LONG).show();
                    }
                        Object StatusValue = childSnapshot.child("Status").getValue();

                        System.out.println(StatusValue);
                    System.out.println(Status);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
    });
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if( id == R.id.StaffSettingsPage){
            Intent intent = new Intent( StaffHomepage.this, StaffSettings.class);
            startActivity(intent);
            return true;
        }
        if( id == R.id.StaffSignOut){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(StaffHomepage.this, Login.class);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

}