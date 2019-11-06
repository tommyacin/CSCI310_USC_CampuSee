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
                sendIntent();
            }
        });
        ImageView img2 = findViewById(R.id.icon2);
        img2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent();
            }
        });
        ImageView img3 = findViewById(R.id.icon3);
        img3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent();
            }
        });
        ImageView img4 = findViewById(R.id.icon4);
        img4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent();
            }
        });
        ImageView img5 = findViewById(R.id.icon5);
        img5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent();
            }
        });

        ImageView img6 = findViewById(R.id.icon6);
        img6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent();
            }
        });
        ImageView img7 = findViewById(R.id.icon7);
        img7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent();
            }
        });
        ImageView img8 = findViewById(R.id.icon8);
        img8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent();
            }
        });
        ImageView img9 = findViewById(R.id.icon9);
        img9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent();
            }
        });
    }

    private void sendIntent(){
        Intent publishEventIntent = new Intent(getApplicationContext(), PublishEvent.class);
        publishEventIntent.putExtra("EVENT_NAME", getIntent().getStringExtra("EVENT_NAME"));
        publishEventIntent.putExtra("EVENT_RADIUS", getIntent().getStringExtra("EVENT_RADIUS"));
        publishEventIntent.putExtra("EVENT_DESCRIPTION", getIntent().getStringExtra("EVENT_DESCRIPTION"));
        publishEventIntent.putExtra("EVENT_HOUR", getIntent().getIntExtra("EVENT_HOUR", 0));
        publishEventIntent.putExtra("EVENT_MINUTE", getIntent().getIntExtra("EVENT_MINUTE", 0));
        publishEventIntent.putExtra("EVENT_MONTH", getIntent().getIntExtra("EVENT_MONTH", 0));
        publishEventIntent.putExtra("EVENT_DAY", getIntent().getIntExtra("EVENT_DAY", 0));
        publishEventIntent.putExtra("EVENT_YEAR", getIntent().getIntExtra("EVENT_YEAR", 0));
        IconPage.this.startActivity(publishEventIntent);
        IconPage.this.finish();
    }
}
