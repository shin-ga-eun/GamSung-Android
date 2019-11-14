package com.example.gamsung;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class MyProfileActivity  extends AppCompatActivity {

    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        gridview = (GridView)findViewById(R.id.gridview);

        MyProfileGridViewAdapter adapter = new MyProfileGridViewAdapter();
        gridview.setAdapter(adapter);

        adapter.addItem("아이유입니다.",R.drawable.img2,20);
        adapter.addItem("아이유입니다.",R.drawable.img2,20);
        adapter.addItem("아이유입니다.",R.drawable.img2,20);
        adapter.addItem("아이유입니다.",R.drawable.img2,20);
        adapter.addItem("아이유입니다.",R.drawable.img2,20);
        adapter.addItem("아이유입니다.",R.drawable.img2,20);
        adapter.addItem("아이유입니다.",R.drawable.img2,20);
        adapter.addItem("아이유입니다.",R.drawable.img2,20);
        adapter.addItem("아이유입니다.",R.drawable.img2,20);

    }

}
