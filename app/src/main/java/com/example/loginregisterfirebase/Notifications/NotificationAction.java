package com.example.loginregisterfirebase.Notifications;

import android.app.NotificationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginregisterfirebase.R;

public class NotificationAction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_action);

        //dismiss notification on Like Button Click
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel((NotificationActivity.NOTIFICATION_ID));

    }
}