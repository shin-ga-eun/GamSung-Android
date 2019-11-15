package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class HashTagActivity extends AppCompatActivity {

    TextView textHashTagName;
    GridView gridview;
    Button btnMainHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtag);

        Intent inIntent = getIntent();
        String hashname = inIntent.getStringExtra("name");

        textHashTagName = (TextView)findViewById(R.id.textHashTagName);
        textHashTagName.setText(hashname);

        ////////게시물 그리드뷰 /////////////////////////////////////////////////////////////////////서버에서 게시글번호를 통해 데이터를 받아야하는지.
        gridview = (GridView)findViewById(R.id.gridview);

        ArrayList<HashTagGridViewItem> hashtagList = new ArrayList<>();
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",30));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",20));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",30));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",20));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));
        hashtagList.add(new HashTagGridViewItem(R.drawable.img2, "아이유",15));


        HashTagGridViewAdapter adapter = new HashTagGridViewAdapter(this, hashtagList);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("iii",""+id);
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                intent.putExtra("cardID",id);
                startActivity(intent);
            }
        });

        btnMainHome = (Button)findViewById(R.id.btnMainHome);
        btnMainHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
