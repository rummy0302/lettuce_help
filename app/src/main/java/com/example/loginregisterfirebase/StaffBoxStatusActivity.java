package com.example.loginregisterfirebase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
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

    RecyclerView myRecyclerView;
    MyAdapter adapter;
    List<MyDataSetGet> listData;
    FirebaseDatabase FDB;
    DatabaseReference DBR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_boxstatus);
//        Toolbar toolbar = findViewById(R.id.Toolbar);
//        setSupportActionBar(toolbar);


        myRecyclerView = findViewById(R.id.myRecycler);
        myRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager LM= new LinearLayoutManager(getApplicationContext());
        myRecyclerView.setLayoutManager(LM);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));

        listData= new ArrayList<>();
        adapter=new MyAdapter(listData);
        FDB =FirebaseDatabase.getInstance("https://loginregister-2f629-default-rtdb.firebaseio.com/");
        GetDataFirebase();
    }

    void GetDataFirebase(){

        DBR= FDB.getReference("Boxes");
        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot,String s) {
                MyDataSetGet data= snapshot.getValue(MyDataSetGet.class);
                listData.add(data);
                myRecyclerView.setAdapter(adapter);
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
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholder>{

        List<MyDataSetGet> listArray;
        public MyAdapter (List<MyDataSetGet> List){
            this.listArray=List;
        }

        @Override
        public MyAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_status_items,parent,false);
            return new MyViewholder(view);
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewholder holder, int position) {
            MyDataSetGet data= listArray.get(position);
            holder.BoxAddress.setText(data.getAddress());
            holder.BoxStatus.setText(data.getStatus());
            holder.BoxPostalCode.setText(data.getBoxPostalCode());

        }

        public class MyViewholder extends RecyclerView.ViewHolder{

            TextView BoxAddress;
            TextView BoxPostalCode;
            TextView BoxStatus;

            public MyViewholder(View itemView){
                super(itemView);
                BoxAddress= itemView.findViewById(R.id.RVBoxAddress);
                BoxPostalCode=itemView.findViewById(R.id.RVPC);
                BoxStatus=itemView.findViewById(R.id.RVBoxStatus);


            }

        }

        @Override
        public int getItemCount() {
            return listArray.size();
        }
    }



}
