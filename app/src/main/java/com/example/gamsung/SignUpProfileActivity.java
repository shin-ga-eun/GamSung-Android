package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpProfileActivity extends AppCompatActivity {

    Button btnSignUpTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupprofile);

        btnSignUpTag = (Button)findViewById(R.id.btnSignUpTag);

        btnSignUpTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpTagActivity.class);
                startActivity(intent);
            }
        });
    }
}