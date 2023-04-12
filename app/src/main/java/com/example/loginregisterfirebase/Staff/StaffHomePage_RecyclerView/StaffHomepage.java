package com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregisterfirebase.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/****

 * adapter: pass listData into MyAdapter and obtains a layout (ViewHolder used for population)
 * LinearLayoutManager: Implementation of the RecyclerView.LayoutManager interface ,used to manage the layout of items in a RecyclerView
 *
 */
public class StaffHomepage extends AppCompatActivity{

    RecyclerView myRecyclerView;

    FirebaseData FirebaseData= new FirebaseData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_homepage);

        myRecyclerView = findViewById(R.id.myRecycler);
        myRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getApplicationContext());
        myRecyclerView.setLayoutManager(LM);

        FirebaseData.GetDataFirebase(myRecyclerView);

    }


}