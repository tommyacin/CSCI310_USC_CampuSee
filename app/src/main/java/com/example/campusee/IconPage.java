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

        ImageView img1 = findViewById(R.id.icon1);
        img1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iconIntent = new Intent(getApplicationContext(), PublishEvent.class);
                IconPage.this.startActivity(iconIntent);
            }
        });
        ImageView img2 = findViewById(R.id.icon2);
        img2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iconIntent = new Intent(getApplicationContext(), PublishEvent.class);
                IconPage.this.startActivity(iconIntent);
            }
        });
        ImageView img3 = findViewById(R.id.icon3);
        img3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iconIntent = new Intent(getApplicationContext(), PublishEvent.class);
                IconPage.this.startActivity(iconIntent);
            }
        });
        ImageView img4 = findViewById(R.id.icon4);
        img4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iconIntent = new Intent(getApplicationContext(), PublishEvent.class);
                IconPage.this.startActivity(iconIntent);
            }
        });
        ImageView img5 = findViewById(R.id.icon5);
        img5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iconIntent = new Intent(getApplicationContext(), PublishEvent.class);
                IconPage.this.startActivity(iconIntent);
            }
        });

        ImageView img6 = findViewById(R.id.icon6);
        img6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iconIntent = new Intent(getApplicationContext(), PublishEvent.class);
                IconPage.this.startActivity(iconIntent);
            }
        });
        ImageView img7 = findViewById(R.id.icon7);
        img7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iconIntent = new Intent(getApplicationContext(), PublishEvent.class);
                IconPage.this.startActivity(iconIntent);
            }
        });
        ImageView img8 = findViewById(R.id.icon8);
        img8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iconIntent = new Intent(getApplicationContext(), PublishEvent.class);
                IconPage.this.startActivity(iconIntent);
            }
        });
        ImageView img9 = findViewById(R.id.icon9);
        img9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iconIntent = new Intent(getApplicationContext(), PublishEvent.class);
                IconPage.this.startActivity(iconIntent);
            }
        });
    }
}
