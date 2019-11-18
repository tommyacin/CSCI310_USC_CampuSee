package com.example.campusee;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentHome extends AppCompatActivity implements HomepageRecyclerAdapter.ItemClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private DatabaseReference mDatabase;
    private ArrayList<Publisher> mAllPublishers;
    private RecyclerView recyclerView;
    public HomepageRecyclerAdapter adapter;

    private List<Geofence> geofences = null;
    private GoogleApiClient googleApiClient = null;
    private Location lastLocation;
    private FusedLocationProviderClient locationClient = null;
    private GeofencingClient geofencingClient;

    private String clickedOnPublisherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Global application = (Global) this.getApplication();

        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        final Intent intent2 = new Intent( this, EventCreatedNotificationService.class);
        startService(intent2);

        boolean fromUserLogin = intent.getExtras().getBoolean("fromUserLogin");
        if (fromUserLogin) {
            String currentUserID = intent.getExtras().getString("currentUserID");
            ((Global) this.getApplication()).setCurrentUserID(currentUserID);
            Log.d("student_home", currentUserID);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAllPublishers = new ArrayList<>();
        grabAllPublishers();

        //notification work
        createGoogleApi();
        createLocationClient();
        createGeofencingClient();

        if (geofences == null) {
            geofences = application.getGeofenceForNotifications();
            if (geofences != null) {
                GeofencingRequest request = new GeofencingRequest.Builder()
                        // Notification to trigger when the Geofence is created
                        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                        .addGeofences(geofences) // add a Geofence
                        .build();
                addGeofence(request);
            }
        }

        //go to notifications screen
        Button studentButton = (Button) findViewById(R.id.notificationToolbarButton);
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent notificationPageIntent = new Intent(getApplicationContext(), NotificationPage.class);
                StudentHome.this.startActivity(notificationPageIntent);
            }
        });

        //go to map screen
        Button mapButton = (Button) findViewById(R.id.mapToolbarButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent mapPageIntent = new Intent(getApplicationContext(), activity_map.class);
                StudentHome.this.startActivity(mapPageIntent);
            }
        });

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                stopService(intent2);
                //stopService(intent3);
                StudentHome.this.startActivity(mainActivityIntent);
            }
        });
    }

    public void onItemClick(View view, int position) {
        grabPublisherClickedOn(adapter.getItem(position).name, position);
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    public void grabPublisherClickedOn(String publisherName, final int position) {
        Query publishers = mDatabase.child("publishers").orderByChild("name").equalTo(publisherName);
        publishers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Publisher publisher = ds.getValue(Publisher.class);

                    clickedOnPublisherId = ds.getKey();
                    Log.d("grabpublisherClickedOn", "publisherID: " + clickedOnPublisherId);
                }

                Intent intent = new Intent(StudentHome.this, publisher_page_of_events.class);
                //intent.putExtra("PUBLISHER_NAME", adapter.getItem(position));
                intent.putExtra("currentPublisherID", clickedOnPublisherId);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError dbe) {
            }
        });
    }

    public void grabAllPublishers() {
        Query publishers = mDatabase.child("publishers");
        publishers.addValueEventListener(new ValueEventListener() {
            // This will get called as many times as there are publishers in the database
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // grab a single publisher
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Publisher publisher = ds.getValue(Publisher.class);
                    Log.d("grabpublishers", "publisher: " + publisher);
                    mAllPublishers.add(publisher);
                }
                recyclerView = findViewById(R.id.rvPublishers);
                recyclerView.setLayoutManager(new LinearLayoutManager(StudentHome.this));
                adapter = new HomepageRecyclerAdapter(StudentHome.this, mAllPublishers);
                adapter.setClickListener(StudentHome.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError dbe) {}
        });
    }

    //notifications stuff
    // Create GoogleApiClient instance
    private void createGoogleApi() {
        Log.d("blah", "createGoogleApi()");
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void createGeofencingClient(){
        Log.d("blah", "createGeofenceClient()");
        if (geofencingClient == null) {
            geofencingClient = LocationServices.getGeofencingClient(this);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        // Disconnect GoogleApiClient when stopping Activity
        googleApiClient.disconnect();
    }

    // GoogleApiClient.ConnectionCallbacks connected
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("tag", "onConnected()");
        getLastKnownLocation();
    }

    // GoogleApiClient.ConnectionCallbacks suspended
    @Override
    public void onConnectionSuspended(int i) {
        Log.w("tag", "onConnectionSuspended()");
    }

    // GoogleApiClient.OnConnectionFailedListener fail
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w("tag", "onConnectionFailed()");
    }

    //location listener
    private void createLocationClient() {
        if (locationClient == null)
            locationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    // Get last known location
    private void getLastKnownLocation() {
        Log.d("tag", "getLastKnownLocation()");
        if (checkPermission()) {
            locationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            lastLocation = location;
                            // Got last known location. In some rare situations, this can be null.
                            if (location != null) {
                                Log.i("tag", "LasKnown location. " +
                                        "Long: " + lastLocation.getLongitude() +
                                        " | Lat: " + lastLocation.getLatitude());
                                startLocationUpdates();
                            } else {
                                Log.w("tag", "No location retrieved yet");
                                startLocationUpdates();
                            }
                        }
                    });
        } else {
            Log.w("tag", "permissionsDenied()");
        }
    }

    private LocationRequest locationRequest;
    // Defined in mili seconds.
    // This number in extremely low, and should be used only for debug
    private final int UPDATE_INTERVAL = 1000;
    private final int FASTEST_INTERVAL = 900;

    // Start location Updates
    private void startLocationUpdates() {
        Log.i("tag", "startLocationUpdates()");
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        if (checkPermission()) {
            locationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
        }

    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                Log.d("tag", "onLocationChanged [" + location + "]");
                lastLocation = location;
            }
        }
    };

    // Check for permission to access Location
    private boolean checkPermission() {
        Log.d("tag", "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("tag", "onLocationChanged ["+location+"]");
        lastLocation = location;
    }


    //creating pending intent
    private PendingIntent geoFencePendingIntent = null;
    public Intent intent3;
    private final int GEOFENCE_REQ_CODE = 0;
    private PendingIntent createGeofencePendingIntent() {
        Log.d("tag", "createGeofencePendingIntent");
        if ( geoFencePendingIntent != null )
            return geoFencePendingIntent;

        intent3 = new Intent( this, GeofenceTransitionService.class);
        return PendingIntent.getService(
                this, GEOFENCE_REQ_CODE, intent3, PendingIntent.FLAG_UPDATE_CURRENT );
    }

    // Add the created GeofenceRequest to the device's monitoring list
    private void addGeofence(GeofencingRequest request) {
        Log.d("tag", "addGeofence");
        if (checkPermission()) {
            geofencingClient.addGeofences(request, createGeofencePendingIntent())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("tag", "success adding geofence");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "fail adding geofence");
                        }
                    });
        }
    }
}






