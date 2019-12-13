package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

//회원가입 첫페이지 -> 아이디, 비밀번호, 닉네임을 입력받아 서버에 저장한다.

public class SignUpActivity extends AppCompatActivity
{

    Button btnSignUpProfile;
    EditText editId, editPw, editName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignUpProfile = (Button)findViewById(R.id.btnSignUpProfile);
        editId = (EditText)findViewById(R.id.editId);
        editPw = (EditText)findViewById(R.id.editPw);
        editName = (EditText)findViewById(R.id.editName);


        btnSignUpProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpProfileActivity.class);
                String identity = editId.getText().toString();
                String password = editPw.getText().toString();
                String nickname = editName.getText().toString();

                String json = "{\"identity\":\""+identity+"\",\"password\":\""+password+"\",\"nickname\":\""+nickname+"\"}";
                intent.putExtra("json", json);
                startActivity(intent);
            }
        });
    }
}
