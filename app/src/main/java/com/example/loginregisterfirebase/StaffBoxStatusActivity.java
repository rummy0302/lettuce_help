package com.example.loginregisterfirebase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class StaffBoxStatusActivity extends AppCompatActivity {

    //RecyclerView: Contains UI
    RecyclerView myRecyclerView;

    //Adapter: manages collection and UI operations. Gets a layout from the current row and pass it to the ViewHolder for population
    MyAdapter adapter;

    // Declare variable 'listData' of type 'List' class that stores objects of the custom data class 'MyDataSetGet'
    List<MyDataSetGet> listData;

    FirebaseDatabase FDB;
    DatabaseReference DBR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Link to layout
        setContentView(R.layout.activity_staff_boxstatus);


        //myRecycler from activity_staff_boxstatus
        myRecyclerView = findViewById(R.id.myRecycler);

        //Recyclerview is fixed in position
        myRecyclerView.setHasFixedSize(true);

        //Creates an instance of a LinearLayoutManager object, which is a built-in implementation of the RecyclerView.LayoutManager interface in Android.
        //The LinearLayoutManager is used to manage the layout of items in a RecyclerView
        RecyclerView.LayoutManager LM= new LinearLayoutManager(getApplicationContext());
        myRecyclerView.setLayoutManager(LM);

        //Instantiate new ArrayList that stores <MyDataSetGet> objects
        listData= new ArrayList<>();

        //Instantiate an adapter that takes in 'listData' parameter as the data source for the adapter
        //Adapters act as bridge between data source and a UI component. In this case, RecyclerView
        adapter=new MyAdapter(listData);

        //Initialize an instance of firebase data
        FDB =FirebaseDatabase.getInstance("https://loginregister-2f629-default-rtdb.firebaseio.com/");

        //Obtain firebase data
        GetDataFirebase();
    }

    void GetDataFirebase(){

        DBR= FDB.getReference("Boxes");




        DBR.addChildEventListener(new ChildEventListener() {

            //The event listener listens to the 'OnChildEvent', which is triggered everytime a new child node is added to the RealTimeDatabase
            @Override
            public void onChildAdded(DataSnapshot snapshot,String s) {
                MyDataSetGet data= snapshot.getValue(MyDataSetGet.class);
                listData.add(data);
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
        List<MyDataSetGet> listArray;

        // 1 arg constructor which takes in a list which stores MyDataSetGet
        public MyAdapter (List<MyDataSetGet> List){
            this.listArray=List;
        }

        // ViewHolder takes individual item from collection of raw data and populates a single row layout .
        @Override
        public MyAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_status_items,parent,false);
            return new MyViewholder(view);
        }


        // bind data to a specific item view(widget) in the RecyclerView at a given position.
        @Override
        public void onBindViewHolder(MyAdapter.MyViewholder holder, int position) {
            MyDataSetGet data= listArray.get(position);
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
