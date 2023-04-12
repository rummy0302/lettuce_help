package com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/****
 * Retrieve data from firebase in the form of a datasnapshot object
 * Convert the datasnapshot object into readable data via .getValue(RecyclerViewItems.class)
 * Add the readable data into an Array List
 * Sort the ArrayList by comparing each RecyclerViewItems Object against the int value "Status"
 * Finally, set the RecyclerView adapter
 ****/
public class FirebaseData {

    FirebaseDatabase FDB;
    DatabaseReference DBR;
    List<RecyclerViewItems> listData;
    MyAdapter adapter;

    public void GetDataFirebase(RecyclerView RV){
        FDB=FirebaseDatabase.getInstance("https://loginregister-2f629-default-rtdb.firebaseio.com/");
        DBR= FDB.getReference().child("Boxes");
        listData = new ArrayList<>();

        adapter = new MyAdapter(listData);
        Log.d(TAG,"Is This working?");


        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String s) {
                RecyclerViewItems data= snapshot.getValue(RecyclerViewItems.class);
                listData.add(data);


                Collections.sort(listData, new Comparator<RecyclerViewItems>() {
                    @Override
                    public int compare(RecyclerViewItems o1, RecyclerViewItems o2) {
                        int status1 = o1.getStatus();
                        int status2 = o2.getStatus();

                        if (status1 < status2) {
                            return -1; // o1 is smaller
                        } else if (status1 > status2) {
                            return 1; // o1 is larger
                        } else {
                            return 0; // o1 and o2 are equal
                        }
                    }
                });
                RV.setAdapter(adapter);



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

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
}}

