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
    private List<GeofenceHolder> userEvents;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Global application = (Global)getApplicationContext();
        userEvents = application.getAllEventsForUser();

        Intent notificationPage = getIntent();
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_map);

        grabAllGeofences();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       // createGeofenceCircles();


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

        createGeofenceMarkers();
        Log.i("Helloo", "map loaded");

        LatLng LA = new LatLng(34.0224, -118.2851);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LA, 15));
    }

    public void createGeofenceMarkers()
    {
        for(int i=0; i<userEvents.size(); i++)
        {
            GeofenceHolder temp = userEvents.get(i);
            Log.i("Added map", "we added something");
            LatLng loc = new LatLng(temp.getLatitude(), temp.getLongitude());

            mMap.addMarker(new MarkerOptions().position(loc).title(temp.getEvent()));
//            mMap.addCircle(new CircleOptions()
//                    .center(loc)
//                    .radius(temp.radius)
//                    .strokeWidth(3f)
//                    .strokeColor(Color.RED)
//                    .fillColor(Color.argb(70,150,50,50)));
        }
//        for(int i=0; i<events.size();i++)
//        {
//            Event currEvent = events.get(i);

//            LatLng loc = new LatLng(currEvent.getLoc()[0], currEvent.getLoc()[1]);
//            LatLng loc = new LatLng(34.0224, -118.2851);
//
//            mMap.addMarker(new MarkerOptions().position(loc).title("RANDO TITLE"));
//            mMap.addCircle(new CircleOptions()
//                    .center(loc)
//                    .radius(200)
//                    .strokeWidth(3f)
//                    .strokeColor(Color.RED)
//                    .fillColor(Color.argb(70,150,50,50)));
    //    }
    }

    public void grabAllGeofences() {
        Query geos = mDatabase.child("geofences");

        geos.addChildEventListener(new ChildEventListener() {

            // This will get called as many times as there are publishers in the database
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // grab a single publisher
                // GeofenceHolder geofence = dataSnapshot.getValue(GeofenceHolder.class);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                long duration = (long)map.get("duration");
                String eventId = (String)map.get("event");
                double latitude = (double)map.get("latitude");
                double longitude = (double)map.get("longitude");
                long radius = (long)map.get("radius");

                GeofenceHolder geofence = new GeofenceHolder(eventId, latitude, longitude, radius, duration);

                userEvents.add(geofence);

                Log.d("grabgeofences", "geofence: " + duration + eventId + latitude + longitude + radius);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LoadPublishers error", "publishers:onCancelled:" + databaseError.getMessage());
            }
        });
    }
}
