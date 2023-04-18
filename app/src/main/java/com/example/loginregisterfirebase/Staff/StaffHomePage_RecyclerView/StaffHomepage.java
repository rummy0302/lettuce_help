package com.example.loginregisterfirebase.Staff.StaffHomePage_RecyclerView;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.loginregisterfirebase.Staff.StaffSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/****

 * adapter: pass listData into MyAdapter and obtains a layout (ViewHolder used for population)
 * LinearLayoutManager: Implementation of the RecyclerView.LayoutManager interface ,used to manage the layout of items in a RecyclerView
 *
 */
//public class StaffHomepage extends AppCompatActivity{
//    private EditText userid, password;
//    private static final String CHANNEL_ID = "channel_id01";
//    public static final int NOTIFICATION_ID = 1;
//    List<RecyclerViewItems> listArray;
//    RecyclerView myRecyclerView;
//
//    private FirebaseAuth authProfile;
//    FirebaseData FirebaseData= new FirebaseData();
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_staff_homepage);
//
//
//        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Staff");
//        int myTitleColor = ContextCompat.getColor(this, R.color.my_title_color);
//        toolbar.setTitleTextColor(myTitleColor);
//
//        myRecyclerView = findViewById(R.id.myRecycler);
//        myRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager LM = new LinearLayoutManager(getApplicationContext());
//        myRecyclerView.setLayoutManager(LM);
//
//        FirebaseData.GetDataFirebase(myRecyclerView);
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//
//        if( id == R.id.StaffSettingsPage){
//            Intent intent = new Intent( StaffHomepage.this, StaffSettings.class);
//            startActivity(intent);
//            return true;
//        }
//        if( id == R.id.StaffSignOut){
//            FirebaseAuth.getInstance().signOut();
//            SharedPreferences sharedpreferences = getSharedPreferences(Login.SHARED_PREFS, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.clear();
//            editor.apply();
//            startActivity(new Intent(StaffHomepage.this, Login.class));
//            finish();
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }
//
//}

public class StaffHomepage extends AppCompatActivity {
    private EditText userid, password;
    private static final String CHANNEL_ID = "channel_id01";
    public static final int NOTIFICATION_ID = 1;
    List<RecyclerViewItems> listArray;
    RecyclerView myRecyclerView;

    private FirebaseAuth authProfile;
    FirebaseData FirebaseData = new FirebaseData();


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

                        if (Status >70.0 && attendingStatus ==false ){
                            String alertBoxName = childSnapshot.getKey();
                            String address = childSnapshot.child("Address").getValue(String.class);
//                            Toast.makeText(StaffHomepage.this,"Box "+alertBoxName+" at "+address+" needs to be collected",Toast.LENGTH_LONG).show();
                            Toast.makeText(StaffHomepage.this,"Box c at 78 Airport Boulevard needs to be collected",Toast.LENGTH_LONG).show();
                            showNotification( address, Status, alertBoxName);

                        } else {
                            Log.e("StaffHomepage", "Address is null for childSnapshot: " + childSnapshot.getKey());
                        }
                    } else {
                        Log.e("StaffHomepage", "StatusValue is null for childSnapshot: " + childSnapshot.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }


    /****
     * The notification system: How does one prevent 2 staff members from travelling to the same box?
     * Solution: If a box reaches >70% capacity, a push notification is sent to every user when the staffhomepage activity is created
     * The push notification prompts the user to attend to the filled box.
     * Should the user accept, the key 'Attending' with Boolean value 'false' will turn 'true' for the filled box object in Realtime database
     * When this boolean value turns true, the notification system will no longer be triggered for all users.
     */
    private void showNotification(String title, Double status, String alertbox) {

        createNotificationChannel();


        Intent mainIntent = new Intent(this, NotificationActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent mainPIntent = PendingIntent.getActivity(this,0,mainIntent,PendingIntent.FLAG_ONE_SHOT);

        Intent likeIntent = new Intent(this, StaffHomepage.class);
        likeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        String userId = alertbox;
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Boxes");
        String key = databaseRef.child(userId).getKey();
        databaseRef.child(userId).child("Attending").setValue(true);


        PendingIntent likePIntent = PendingIntent.getActivity(this,0,likeIntent,PendingIntent.FLAG_ONE_SHOT);

        //click dislike button to start dislikeactivity
        Intent disIntent = new Intent(this,NotificationAction2.class);
        disIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent dislikePIntent = PendingIntent.getActivity(this,0,disIntent,PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle(title);
        builder.setContentText("Box is getting full. Please come and collect it!");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);
        builder.setContentIntent(mainPIntent);

        //add action buttons to notification
        builder.addAction(R.drawable.ic_like,"Accept",likePIntent);
        builder.addAction(R.drawable.ic_dislike,"Decline",dislikePIntent);

        //notification manager
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
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
            Toast.makeText(StaffHomepage.this,"Signout Succesful",Toast.LENGTH_SHORT).show();
            SharedPreferences sharedpreferences = getSharedPreferences(Login.SHARED_PREFS, android.content.Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            finish();
            startActivity(new Intent(StaffHomepage.this, Login.class));
        }


        return super.onOptionsItemSelected(item);
    }

}