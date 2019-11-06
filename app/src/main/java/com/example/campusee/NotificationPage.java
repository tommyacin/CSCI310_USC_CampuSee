/*package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

public class NotificationPage extends AppCompatActivity {
    private DatabaseReference mUserNotificationsReference;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getting to notification page
        Intent homePage = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

       /* Button studentButton = (Button) findViewById(R.id.publishersToolbarButton);
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent publisherPageIntent = new Intent(getApplicationContext(), StudentHome.class);
                NotificationPage.this.startActivity(publisherPageIntent);
            }
        });

        Button mapButton = (Button) findViewById(R.id.mapToolbarButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent mapPageIntent = new Intent(getApplicationContext(), activity_map.class);
                NotificationPage.this.startActivity(mapPageIntent);
            }
        });*/
package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusee.PublisherRecyclerviewAdapter;
import com.example.campusee.R;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import android.widget.Button;

public class NotificationPage extends AppCompatActivity implements PublisherRecyclerviewAdapter.ItemClickListener {

    private DatabaseReference mDatabase;
    ArrayList<Notification> mAllNotifs;
    ArrayList<String> notifNames = new ArrayList<>();
    RecyclerView recyclerView;
    PublisherRecyclerviewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent homePage = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        // Initialize Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String currentUserID = ((Global) this.getApplication()).getCurrentUserID();

        // Grabs all user notifications and displays them in recycler view
        grabAllUserNotifications(currentUserID);

        Button studentButton = (Button) findViewById(R.id.publishersToolbarButton);
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent studentHomeIntent = new Intent(getApplicationContext(), StudentHome.class);
                studentHomeIntent.putExtra("fromUserLogin", false);
                NotificationPage.this.startActivity(studentHomeIntent);
            }
        });

        Button mapButton = (Button) findViewById(R.id.mapToolbarButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent mapPageIntent = new Intent(getApplicationContext(), activity_map.class);
                mapPageIntent.putExtra("fromUserLogin", false);
                NotificationPage.this.startActivity(mapPageIntent);
            }
        });

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                stopService(new Intent(getApplicationContext(), EventCreatedNotificationService.class));
                NotificationPage.this.startActivity(mainActivityIntent);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStart(){
        super.onStart();

    }

    public void grabAllUserNotifications(String userId) {
        Query userNotifs = mDatabase.child("user-notifications").child(userId);

        userNotifs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Notification notif = ds.getValue(Notification.class);

                    mAllNotifs.add(notif);
                    notifNames.add(notif.eventId.title);
                }

                // set up the RecyclerView
                recyclerView = findViewById(R.id.rvAnimals);
                recyclerView.setLayoutManager(new LinearLayoutManager(NotificationPage.this));
                adapter = new PublisherRecyclerviewAdapter(NotificationPage.this, notifNames);
                adapter.setClickListener(NotificationPage.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError dbe) {

            }
        });
    }

}





