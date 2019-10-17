package com.example.gamsung;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpTagActivity extends AppCompatActivity {

    Button btnLogin;

    View dialogView;

    ListView listview;
    SignUpListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuptag);

//        //Adapter 생성
//        adapter = new SignUpListViewAdapter(this);
//
//        //리스트뷰 참조 및 Adapter 달기
//        listview = (ListView)findViewById(R.id.listview);
//        listview.setAdapter(adapter);




    }

}

