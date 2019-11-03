package com.example.campusee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class IconPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_page);

        ImageView img = findViewById(R.id.icon1);
        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iconIntent = new Intent(getApplicationContext(), PublishEvent.class);
                IconPage.this.startActivity(iconIntent);
            }
        });
    }
}
