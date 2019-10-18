package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpTagActivity extends AppCompatActivity {

    Button btnLogin, btnSelectCompleted;
    TextView textTag1, textTag2, textTag3;

    ListView listview;
    SignUpListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuptag);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnSelectCompleted = (Button)findViewById(R.id.btnSelectCompleted);

        textTag1 = (TextView)findViewById(R.id.textTag1);
        textTag2 = (TextView)findViewById(R.id.textTag2);
        textTag3 = (TextView)findViewById(R.id.textTag3);

        //Adapter 생성
        adapter = new SignUpListViewAdapter();

        //리스트뷰 참조 및 Adapter 달기
        listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(adapter);

        //서버에서 가져오기 (해시태그서버)/////////////////////////////////
        adapter.addItem("연애");
        adapter.addItem("이별");
        adapter.addItem("학교");
        adapter.addItem("피카츄");
        adapter.addItem("카트");
        adapter.addItem("조정석");
        adapter.addItem("노래");
        adapter.addItem("뮤지컬");
        adapter.addItem("강하늘");
        adapter.addItem("공효진");
        adapter.addItem("아이유");

        btnSelectCompleted.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                int count = adapter.getCount() ;
                String[] tagValue = new String[3]; //체크된 해시태그텍스트 저장 변수
                int j = 0;

                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        //3개 선택시
                        if(j<3) {
                            Log.d("checked position", Integer.toString(i));
                            tagValue[j] = adapter.getHashTagNameByPosition(i);
                            Log.d("checked getHashTagNameByPosition", tagValue[j]);
                            j = j + 1;

                            btnLogin.setVisibility(View.VISIBLE); //btnLogin Visible
                        }
                        else{
                            //3개이상 선택시 체크값 초기화
                            Toast.makeText(getApplicationContext(), "태그를 3개만 선택하세요!", Toast.LENGTH_SHORT).show();
                            for (int k=0; k<count; k++) {
                                listview.setItemChecked(k, false) ;
                            }
                            btnLogin.setVisibility(View.INVISIBLE); //btnLogin InVisible
                        }
                    }
                }

                textTag1.setText(tagValue[0]);
                textTag2.setText(tagValue[1]);
                textTag3.setText(tagValue[2]);

            }
        });

        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });


    }

}

