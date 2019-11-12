package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class WriteActivity extends AppCompatActivity {

    ImageView imgCard;
    Button btnMainHome, btnWriteImage;
    EditText edtCard;

    String content; //카드내용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        imgCard = (ImageView)findViewById(R.id.imgCard);
        btnWriteImage = (Button)findViewById(R.id.btnWriteImage);
        btnMainHome = (Button)findViewById(R.id.btnMainHome);
        edtCard = (EditText)findViewById(R.id.edtCard);

        imgCard.setAlpha(0.5f); //이미지 투명도조절

        //나가기버튼 클릭시, 메인홈으로 이동
        btnMainHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), MainHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //이미지선택화면으로 이동 ->  content(카드내용) 넘겨줌 (임시)
        btnWriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = edtCard.getText().toString();

                WriteContentAnalysis wc = new WriteContentAnalysis(content); //값을 보냄?????????????????????????????

                Intent intent = new Intent (getApplicationContext(), WriteImageActivity.class);
                intent.putExtra("content",content);
                startActivity(intent);
                finish();
            }
        });



    }
}