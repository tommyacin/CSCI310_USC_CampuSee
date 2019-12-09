package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PublisherProfilePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publisher_profile_page);

        final String currentPublisherName = intent.getExtras().getString("currentPublisherName");
        final String currentPublisherEmail = intent.getExtras().getString("currentPublisherEmail");
        final String currentPublisherID = ((Global) this.getApplication()).getCurrentPublisherID();

        final TextView publisherName = findViewById(R.id.PublisherName);
        publisherName.setText(intent.getExtras().getString("currentPublisherName"), TextView.BufferType.EDITABLE);
        TextView publisherEmail = findViewById(R.id.publisherEmail);
        publisherEmail.setText(intent.getExtras().getString("currentPublisherEmail"), TextView.BufferType.EDITABLE);


        Button createButton = (Button) findViewById(R.id.saveProfileInfo);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent createIntent = new Intent(getApplicationContext(), PublisherMain.class);
                String name = publisherName.getText().toString();
                createIntent.putExtra("currentPublisherID", currentPublisherID);
                createIntent.putExtra("currentPublisherName", name);
                createIntent.putExtra("currentPublisherEmail", currentPublisherEmail);
                PublisherProfilePage.this.startActivity(createIntent);
            }
        });


    }


    @Override
    public void onStart(){
        super.onStart();
    }

}
