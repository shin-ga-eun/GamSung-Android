package com.example.gamsung;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gamsung.Main.MainHome.MainHomeActivity;
import com.example.gamsung.SignUp.SignUpActivity;
import com.example.gamsung.controller.UserController;
import com.example.gamsung.domain.dto.user.LoginDto;

import androidx.appcompat.app.AppCompatActivity;

//아이디, 비밀번호를 입력받아 서버에 전송 후 로그인 승인을 한다.

public class LoginActivity extends AppCompatActivity {

    Button btnStart, btnSignUp;
    EditText editId, editPw;
    private LoginDto loginDto;
    private UserController userController;

    public static String loginCheck; //전역변수 선언 -> 로그인 성공 시, 아이디값을 공유하기 위함
    private SharedPreferences userInfo;
    private SharedPreferences.Editor loginEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnStart = (Button)findViewById(R.id.btnStart);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        editId = (EditText)findViewById(R.id.editId);
        editPw = (EditText)findViewById(R.id.editPw);

        userController = new UserController(getApplicationContext());
        userInfo = getSharedPreferences("UserInformation", Activity.MODE_PRIVATE);
        loginEditor = userInfo.edit();


        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                loginDto = new LoginDto(editId.getText().toString(), editPw.getText().toString());
                userController.login(loginDto);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(loginCheck.equals(loginDto.getIdentity())){

                            loginEditor.putString("identity", editId.getText().toString());
                            loginEditor.commit();
                            Toast.makeText(LoginActivity.this,"로그인 성공", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(getApplicationContext(), MainHomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 2000);

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
