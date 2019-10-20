package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HashTagActivity extends AppCompatActivity {

    TextView textHashTagName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtag);

        Intent inIntent = getIntent();
        String hashname = inIntent.getStringExtra("name");

        //Log.d("ddd",hashname);

        textHashTagName = (TextView)findViewById(R.id.textHashTagName);
        textHashTagName.setText(hashname);
    }
}
