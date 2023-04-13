package com.example.loginregisterfirebase;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.loginregisterfirebase.R;

public class NotificationActivity extends AppCompatActivity {

    //there can be multiple notifications so it can be used as notification identity
    private static final String CHANNEL_ID = "channel_id01";
    public static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Button showNotificationBtn = findViewById(R.id.showNotificationBtn);
        showNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // click button to show notification
                showNotification();

            }
        });
    }


    private void showNotification() {

        createNotificationChannel();

        //start this Activity on by tapping notification
        Intent mainIntent = new Intent(this,NotificationActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent mainPIntent = PendingIntent.getActivity(this,0,mainIntent,PendingIntent.FLAG_ONE_SHOT);

        //click like button to start likeactivity
        Intent likeIntent = new Intent(this,StaffHomepage.class);
        likeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent likePIntent = PendingIntent.getActivity(this,0,likeIntent,PendingIntent.FLAG_ONE_SHOT);


        //click dislike button to start dislikeactivity
        Intent disIntent = new Intent(this,NotificationAction2.class);
        disIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent dislikePIntent = PendingIntent.getActivity(this,0,disIntent,PendingIntent.FLAG_ONE_SHOT);




        // creating notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        //icon
        builder.setSmallIcon(R.drawable.ic_notification);

        //title
        builder.setContentTitle("Title of Notification");

        //description
        builder.setContentText("This is the description of notification");

        //set priority
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //dismiss on tap
        builder.setAutoCancel(true);

        //start intent on notification tap (Notification Activity)
        builder.setContentIntent(mainPIntent);

        //add action buttons to notification
        builder.addAction(R.drawable.ic_like,"Like",likePIntent);
        builder.addAction(R.drawable.ic_dislike,"Dislike",dislikePIntent);



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
}