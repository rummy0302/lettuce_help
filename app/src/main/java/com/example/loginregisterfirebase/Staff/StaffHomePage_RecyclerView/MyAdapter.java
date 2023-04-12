package com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregisterfirebase.R;

import java.util.List;

/****
 * MyAdapter takes in an Arraylist from FirebaseData.class and binds the data to the ViewHolder
****/
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholder>{

    List<RecyclerViewItems> listArray;

    public MyAdapter (List<RecyclerViewItems> List){
        this.listArray=List;
    }
    public class MyViewholder extends RecyclerView.ViewHolder{
        TextView BoxAddress,BoxPostalCode,BoxCapacity,BoxUnitNumber;
        ProgressBar BoxStatusBar;

        // ViewHolder takes individual item from collection of raw data and populates a single row layout .
        public MyViewholder(View itemView){
            super(itemView);
            BoxAddress= itemView.findViewById(R.id.recyclerViewBoxAddress);
            BoxUnitNumber=itemView.findViewById(R.id.recyclerViewUnitNumber);
            BoxPostalCode=itemView.findViewById(R.id.recyclerViewBoxPostalCode);
            BoxStatusBar =itemView.findViewById(R.id.recyclerViewProgressBar);
            BoxCapacity=itemView.findViewById(R.id.recyclerViewBoxStatus);

        }

    }

    @Override
    public int getItemCount() {
        return listArray.size();
    }

    @Override
    public MyAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_staff_recyclerview_items,parent,false);
        return new MyViewholder(view);
    }

    /****
     *  Calculation:
     *  Height of Demo Box = 25cm
     *  Sensor, placed on the underside of box lid, detects distance from ground
     *  If sensor distance = 0 , it means boxes are at full capacity
     *  Formula: (100 - (distance/25 *100) ) %
    ****/
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewholder holder, int position) {
        RecyclerViewItems data= listArray.get(position);
        holder.BoxAddress.setText(data.getAddress());
        holder.BoxUnitNumber.setText(data.getUnitNumber());
        holder.BoxPostalCode.setText(data.getBoxPostalCode());
        holder.BoxStatusBar.setProgress(100-(data.getStatus()*4));
        holder.BoxCapacity.setText(100-(data.getStatus()*4)+"%");

    }



}