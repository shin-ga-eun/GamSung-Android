package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import androidx.appcompat.app.AppCompatActivity;

public class WriteImageActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnWrite;
    TextView textCard;
    ImageView imgCard;

    ChipGroup chipGroup;

    String content; //카드내용 받는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writeimage);

        imgCard = (ImageView)findViewById(R.id.imgCard);
        textCard = (TextView)findViewById(R.id.textCard);
        btnWrite = (Button)findViewById(R.id.btnWrite);

        chipGroup = findViewById(R.id.chipGroup);

        //나가기버튼 클릭시, 메인홈으로 이동
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), WriteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        content = intent.getStringExtra("content");

        //content에 널값이 넘어왔을 때 처리
        if(content.getBytes().length <= 0){
            Toast.makeText(getApplicationContext(), "카드내용을 입력하지 않았습니다.", Toast.LENGTH_LONG).show();
            content = "자유롭게 글을 써보세요...";
        }

        textCard.setText(content);
        imgCard.setAlpha(0.5f);

        //////////////////////////////chipGroup에 chip추가 구현///////////////////////////////////
        String array[] = {"피카츄","꼬부기","파이리","이상해씨"}; //자바정규식으로부터 배열받을 예정.

        for(int i=0; i<4; i++){
            Chip chip = new Chip(this);
            chip.setText(array[i]);
            chip.setCloseIconVisible(true);
            chip.setCheckable(false);
            chip.setClickable(false);
            chip.setOnCloseIconClickListener(this);
            chipGroup.addView(chip);
        }

        chipGroup.setVisibility(View.VISIBLE);
    }

    //chip onClick메소드 -> 삭제
    @Override
    public void onClick(View v) {
        Chip chip = (Chip)v;
        chipGroup.removeView(chip);
    }
}
