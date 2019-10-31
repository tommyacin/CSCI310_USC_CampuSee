/*package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationPage extends AppCompatActivity {
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
import android.view.View;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusee.PublisherRecyclerviewAdapter;
import com.example.campusee.R;

import java.util.ArrayList;
import android.widget.Button;

public class NotificationPage extends AppCompatActivity implements PublisherRecyclerviewAdapter.ItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent homePage = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);
        PublisherRecyclerviewAdapter adapter;
        // data to populate the RecyclerView with
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        ArrayList<String> animalNames = new ArrayList<>();
        //adapter = new PublisherRecyclerviewAdapter(this, animalNames);
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");
        //adapter.notifyDataStateChanged();
        // set up the RecyclerView

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PublisherRecyclerviewAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        Button studentButton = (Button) findViewById(R.id.publishersToolbarButton);
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
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

    }
}





