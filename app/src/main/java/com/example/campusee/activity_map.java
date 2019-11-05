package com.example.campusee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class activity_map extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent notificationPage = getIntent();
        super.onCreate(savedInstanceState);
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

        LatLng LA = new LatLng(34.0224, -118.2851);
        mMap.addMarker(new MarkerOptions().position(LA).title("Marker in LA"));
        mMap.addCircle(new CircleOptions()
                            .center(LA)
                            .radius(200)
                            .strokeWidth(3f)
                            .strokeColor(Color.RED)
                            .fillColor(Color.argb(70,150,50,50)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LA, 15));

    }

    public void createGeofenceCircles(List<Event> events)
    {
        for(int i=0; i<events.size();i++)
        {
            Event currEvent = events.get(i);

            LatLng loc = currEvent.getLoc();
            mMap.addMarker(new MarkerOptions().position(loc).title(currEvent.getTitle()));
            mMap.addCircle(new CircleOptions()
                    .center(loc)
                    .radius(currEvent.getRadius())
                    .strokeWidth(3f)
                    .strokeColor(Color.RED)
                    .fillColor(Color.argb(70,150,50,50)));
        }
    }
}
