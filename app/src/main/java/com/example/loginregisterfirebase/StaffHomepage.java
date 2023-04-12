package com.example.loginregisterfirebase;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class StaffHomepage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    private DrawerLayout drawerLayoutt;
    private ActionBarDrawerToggle drawerToggle;

    RecyclerView myRecyclerView;
    MyAdapter adapter;
    List<RecyclerViewItems> listData;
    FirebaseDatabase FDB;
    DatabaseReference DBR;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_homepage);



        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.menu_Close);

        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle =  new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_Open,R.string.menu_Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#aed19d"))); // Change to your desired color
        getSupportActionBar().setTitle("Lettuce Help");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.StaffSettingsPage:
                        startActivity(new Intent(StaffHomepage.this, StaffSettings.class));
                        Log.i("Menu_drawer_tag","Staff Settings is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.StaffLogOut:
                        Log.i("Menu_drawer_tag","Staff Log out is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }



                return true;
            }
        });

        myRecyclerView = findViewById(R.id.myRecycler);
        myRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getApplicationContext());
        myRecyclerView.setLayoutManager(LM);
        listData = new ArrayList<>();
        adapter = new MyAdapter(listData);
        FDB = FirebaseDatabase.getInstance("https://loginregister-2f629-default-rtdb.firebaseio.com/");
        GetDataFirebase();

        final Button mapStaffBtn = findViewById(R.id.mapStaffBtn);
        final Button boxStaffBtn = findViewById(R.id.boxStaffBtn);
        final Button settingsStaffBtn = findViewById(R.id.settingsStaffBtn);

        mapStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.scheme("geo").opaquePart("0.0")
                        .appendQueryParameter("q", "ChangiAirport");

                Uri uri = uriBuilder.build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        boxStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StaffHomepage.this, "You are in the homescreen", Toast.LENGTH_SHORT).show();
            }
        });

        settingsStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffHomepage.this, StaffSettings.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START)))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    void GetDataFirebase(){

        DBR= FDB.getReference("Boxes");

        DBR.addChildEventListener(new ChildEventListener() {

            //The event listener listens to the 'OnChildEvent', which is triggered everytime a new child node is added to the RealTimeDatabase
            @Override
            public void onChildAdded(DataSnapshot snapshot,String s) {
                RecyclerViewItems data= snapshot.getValue(RecyclerViewItems.class);


                //add data to listData
                listData.add(data);

                //sort data
                Collections.sort(listData, new Comparator<RecyclerViewItems>() {
                    @Override
                    public int compare(RecyclerViewItems o1, RecyclerViewItems o2) {
                        return o1.getStatus().compareTo(o2.getStatus());
                    }
                });


                myRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    //Defines a custom adapter class 'MyAdapter' that extends 'RecyclerView.Adapter<>' which is
    //a base class required for implementing adapters for recycler view
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholder>{

        // Define a variable 'list Array' which stores MyDataSetGet objects
        List<RecyclerViewItems> listArray;

        // 1 arg constructor which takes in a list which stores MyDataSetGet
        public MyAdapter (List<RecyclerViewItems> List){
            this.listArray=List;
        }

        // ViewHolder takes individual item from collection of raw data and populates a single row layout .
        @Override
        public MyAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items,parent,false);
            return new MyViewholder(view);
        }


        // bind data to a specific item view(widget) in the RecyclerView at a given position.
        @Override
        public void onBindViewHolder(MyAdapter.MyViewholder holder, int position) {
            RecyclerViewItems data= listArray.get(position);
            holder.BoxAddress.setText(data.getAddress());
            holder.BoxStatusBar.setProgress(100-data.getStatus());
            holder.BoxPostalCode.setText(data.getBoxPostalCode());
            holder.BoxStatusString.setText(String.valueOf(100-(data.getStatus()))+"%");


        }

        public class MyViewholder extends RecyclerView.ViewHolder{

            TextView BoxAddress;
            TextView BoxPostalCode;

            TextView BoxStatusString;
            ProgressBar BoxStatusBar;

            public MyViewholder(View itemView){
                super(itemView);
                BoxAddress= itemView.findViewById(R.id.RVBoxAddress);
                BoxPostalCode=itemView.findViewById(R.id.RVPC);
                BoxStatusBar =itemView.findViewById(R.id.RVBoxStatus);
                BoxStatusString =itemView.findViewById(R.id.RVStatusI);


            }

        }

        @Override
        public int getItemCount() {
            return listArray.size();
        }



    }



}