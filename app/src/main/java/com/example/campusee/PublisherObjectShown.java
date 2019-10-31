package com.example.campusee;

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

public class PublisherObjectShown extends AppCompatActivity implements PublisherRecyclerviewAdapter.ItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

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


    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
