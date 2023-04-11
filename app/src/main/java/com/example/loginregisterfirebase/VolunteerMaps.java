package com.example.loginregisterfirebase;

import android.os.Bundle;





import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.widget.FrameLayout;
        import android.widget.Toast;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;

public class VolunteerMaps extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap   gMap;
    FrameLayout map;

    //VARS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        map = findViewById(R.id.map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;

        LatLng mapIndia = new LatLng(1.371730, 103.847641);
        this.gMap.addMarker(new MarkerOptions().position(mapIndia).title("Marker in india"));
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(mapIndia));
    }


}