package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gamsung.controller.UserController;
import com.example.gamsung.domain.dto.user.LoginDto;

import androidx.appcompat.app.AppCompatActivity;

//아이디, 비밀번호를 입력받아 서버에 전송 후 로그인 승인을 한다.

public class LoginActivity extends AppCompatActivity {

    Button btnStart, btnSignUp;
    EditText editId, editPw;
    private LoginDto loginDto;
    private UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnStart = (Button)findViewById(R.id.btnStart);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        editId = (EditText)findViewById(R.id.editId);
        editPw = (EditText)findViewById(R.id.editPw);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginDto = new LoginDto(editId.getText().toString(), editPw.getText().toString());
                //userController.login(loginDto);


                Intent intent = new Intent(getApplicationContext(),MainHomeActivity.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
