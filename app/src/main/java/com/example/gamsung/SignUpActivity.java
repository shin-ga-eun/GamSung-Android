package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//회원가입 첫페이지 -> 아이디, 비밀번호, 닉네임을 입력받아 서버에 저장한다.

public class SignUpActivity extends AppCompatActivity
{

    Button btnSignUpProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignUpProfile = (Button)findViewById(R.id.btnSignUpProfile);

        btnSignUpProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
