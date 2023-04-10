package com.example.loginregisterfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.app.Dialog;
        import android.content.Intent;
import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.GoogleApiAvailability;

public class Maps1 extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    Button showMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.del_activity_maps1);

        showMap = findViewById(R.id.showMap);
        showMap.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Maps1.this, VolunteerMaps.class);
                startActivity(intent);
            }
        }));
    }
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Maps1.this);

        if(available== ConnectionResult.SUCCESS){
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occurred but we can fix it ");
            Dialog dialog =  GoogleApiAvailability.getInstance().getErrorDialog(Maps1.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();

        }
        else{
            Toast.makeText(this, "We can't make", Toast.LENGTH_SHORT).show();

        }
        return false;
    }
}