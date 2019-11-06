package com.example.campusee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class activity_map extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private List<String> existingPublishers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Global application = (Global)getApplicationContext();
        existingPublishers = application.getExistingPublishers();

        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button notificationButton = (Button) findViewById(R.id.notificationToolbarButton);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent notificationPageIntent = new Intent(getApplicationContext(), NotificationPage.class);
                notificationPageIntent.putExtra("fromUserLogin", false);
                activity_map.this.startActivity(notificationPageIntent);
            }
        });

        Button studentHomeButton = (Button) findViewById(R.id.publishersToolbarButton);
        studentHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent studentHomeIntent = new Intent(getApplicationContext(), StudentHome.class);
                studentHomeIntent.putExtra("fromUserLogin", false);
                activity_map.this.startActivity(studentHomeIntent);
            }
        });

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        createBuildingMarkers();
        Log.i("Helloo", "map loaded");

        LatLng LA = new LatLng(34.0224, -118.2851);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LA, 15));
    }

    public void createBuildingMarkers()
    {
        for(int i=0; i<existingPublishers.size(); i++)
        {
            String temp = existingPublishers.get(i);
            double latitude = Constants.allBuildings.get(temp).latLoc;
            double longitude = Constants.allBuildings.get(temp).longLoc;
            //latlng using map in constants
            LatLng loc = new LatLng(latitude, longitude);

            mMap.addMarker(new MarkerOptions().position(loc).title(temp));
        }

    }


}
