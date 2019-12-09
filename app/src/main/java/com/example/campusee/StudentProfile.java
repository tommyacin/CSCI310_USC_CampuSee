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

public class StudentProfile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile_page);

        final String currentUserName = intent.getExtras().getString("currentUserName");
        final String currentUserEmail = intent.getExtras().getString("currentUserEmail");
        final String currentUserID = intent.getExtras().getString("currentUserID");


        final TextView userName = findViewById(R.id.StudentName);
        userName.setText(intent.getExtras().getString("currentUserName"), TextView.BufferType.EDITABLE);
        TextView userEmail = findViewById(R.id.studentEmail);
        userEmail.setText(intent.getExtras().getString("currentUserEmail"), TextView.BufferType.EDITABLE);


        Button createButton = (Button) findViewById(R.id.saveProfileInfo);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add code here for what will happen when the user selects the student button
                Intent createIntent = new Intent(getApplicationContext(), StudentHome.class);
                String name = userName.getText().toString();
                createIntent.putExtra("currentUserID", currentUserID);
                createIntent.putExtra("fromUserLogin", false);
                createIntent.putExtra("currentUserEmail", currentUserEmail);
                createIntent.putExtra("currentUserName", name);
                StudentProfile.this.startActivity(createIntent);
            }
        });


    }


}
