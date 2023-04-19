package com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginregisterfirebase.Login;
import com.example.loginregisterfirebase.NotificationAction2;
import com.example.loginregisterfirebase.NotificationActivity;
import com.example.loginregisterfirebase.R;
import com.example.loginregisterfirebase.Register;
import com.example.loginregisterfirebase.Report_Fields;
import com.example.loginregisterfirebase.Staff.StaffReport;
import com.example.loginregisterfirebase.Staff.StaffSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private EditText userid, password;
    private static final String CHANNEL_ID = "channel_id01";
    public static final int NOTIFICATION_ID = 1;
    List<RecyclerViewItems> listArray;
    RecyclerView myRecyclerView;

    private FirebaseAuth authProfile;
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
                    Integer statusValue = childSnapshot.child("Status").getValue(Integer.class);
                    Boolean attendingStatus=childSnapshot.child("Attending").getValue(Boolean.class);

                    if (statusValue != null ) {
                        double Status = (statusValue / 25.0) * 100.0;


                        if (Status >70.0 && attendingStatus ==false){

                            String alertBoxName = childSnapshot.getKey();
                            String address = childSnapshot.child("Name").getValue(String.class);
                            System.out.println(alertBoxName);
                            System.out.println(address);
                            Toast.makeText(StaffHomepage.this,address+"Box needs to be collected",Toast.LENGTH_LONG).show();
                            showNotification( address, Status, alertBoxName);




//                        if (childSnapshot.hasChild("Address") && childSnapshot.child("Address").getValue() != null) {
//                            String alertBoxName = childSnapshot.getKey();
//                            String address = childSnapshot.child("Address").getValue(String.class);
//                            System.out.println(alertBoxName);
//                            showNotification(alertBoxName, status);
//                            Toast.makeText(StaffHomepage.this, alertBoxName + " Box needs to be collected", Toast.LENGTH_LONG).show();
//

                        } else {
                            // Handle case where Address is null
                            // Log an error or perform other appropriate actions
                            Log.e("StaffHomepage", "Address is null for childSnapshot: " + childSnapshot.getKey());
                        }

//                        System.out.println(statusValue);
//                        System.out.println(Status);
                    } else {
                        // Handle case where statusValue is null
                        // Log an error or perform other appropriate actions
                        Log.e("StaffHomepage", "StatusValue is null for childSnapshot: " + childSnapshot.getKey());
                    }

//                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
//                        double Status= (((childSnapshot.child("Status").getValue(Integer.class))/25.0)*100.0);
//
////                    for (DataSnapshot grandChildSnapshot: childSnapshot.getChildren()){
////                        if (grandChildSnapshot.hasChild("Status")){
//
//                        if (Status >70.0){
//
//                            String AlertBoxName= (((childSnapshot.child("Address").getValue(String.class))));
//                            System.out.println(AlertBoxName);
//                            Toast.makeText

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }



    private void showNotification(String title, Double status, String alertbox) {

        createNotificationChannel();

        //start this Activity on by tapping notification
        Intent mainIntent = new Intent(this, NotificationActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent mainPIntent = PendingIntent.getActivity(this,0,mainIntent,PendingIntent.FLAG_ONE_SHOT);

        //click like button to start likeactivity
        Intent likeIntent = new Intent(this, StaffHomepage.class);
        likeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);




//        String userId = alertbox; // Replace with the appropriate user ID
//        String itemId = status+""; // Replace with the appropriate item ID
//        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Boxes");
//        String key = databaseRef.child(userId).getKey();
//        databaseRef.child(userId).child(key).setValue(itemId);
//        System.out.println(key);
//        databaseRef.child(userId).child("Attending").setValue(true);
//        Button BoxBtn=findViewById(R.id.recylcerViewBtn);
//        int greenclr = ContextCompat.getColor(this, R.color.green);
//        BoxBtn.setBackgroundColor(greenclr);



        PendingIntent likePIntent = PendingIntent.getActivity(this,0,likeIntent,PendingIntent.FLAG_ONE_SHOT);

        //click dislike button to start dislikeactivity
        Intent disIntent = new Intent(this,NotificationAction2.class);
        disIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent dislikePIntent = PendingIntent.getActivity(this,0,disIntent,PendingIntent.FLAG_ONE_SHOT);


        // creating notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        //icon
        builder.setSmallIcon(R.drawable.ic_notification);

        builder.setContentTitle(title);
        //title
///        builder.setContentTitle("Title of Notification");

        //description
        builder.setContentText("Box is getting full. Please come and collect it!");

        //set priority
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //dismiss on tap
        builder.setAutoCancel(true);

        //start intent on notification tap (Notification Activity)
        builder.setContentIntent(mainPIntent);

        //add action buttons to notification
        builder.addAction(R.drawable.ic_like,"Accept",likePIntent);
        builder.addAction(R.drawable.ic_dislike,"Decline",dislikePIntent);



        //notification manager
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());





    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name  = "My Notification";
            String description = "My Notification description";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.createNotificationChannel(notificationChannel);

        }
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