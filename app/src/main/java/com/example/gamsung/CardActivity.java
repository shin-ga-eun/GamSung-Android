package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CardActivity  extends AppCompatActivity {

    TextView textID;
    Long cardID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        textID = (TextView)findViewById(R.id.textID);

        Intent inIntent = getIntent();
        cardID = inIntent.getLongExtra("cardID",0);
        
        textID.setText(""+cardID);


    }
}
