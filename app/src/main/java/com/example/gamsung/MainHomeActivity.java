package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;

public class MainHomeActivity extends AppCompatActivity implements MainHomeListViewAdapter.ListBtnClickListener{

    Button btnMyProfile;
    Button btnMain, btnSearch, btnCard, btnTimeLine, btnChat; // 하단버튼목록들

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);

        /////////////////////////////////////////////////////////////btnMyProfile////////
        btnMyProfile = (Button) findViewById(R.id.btnMyProfile);

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(intent);
            }
        });

        /////////////////////////////////////////////////////////////하단버튼목록들////////
        btnMain = (Button) findViewById(R.id.btnMain);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnCard = (Button) findViewById(R.id.btnCard);
        btnTimeLine = (Button) findViewById(R.id.btnTimeLine);
        btnChat = (Button) findViewById(R.id.btnChat);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainHomeActivity.class);
                startActivity(intent);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HashSearchActivity.class);
                startActivity(intent);
            }
        });
        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });
        btnTimeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimeLineActivity.class);
                startActivity(intent);
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });

        ////////////////////////////////////////////////////////////adapter//////////////
        final MainHomeListViewAdapter adapter1, adapter2, adapter3;
        final ListView listview1, listview2, listview3;

        adapter1 = new MainHomeListViewAdapter(this);
        listview1 = (ListView) findViewById(R.id.listview1);
        listview1.setAdapter(adapter1);

        adapter2 = new MainHomeListViewAdapter(this);
        listview2 = (ListView) findViewById(R.id.listview2);
        listview2.setAdapter(adapter2);

        adapter3 = new MainHomeListViewAdapter(this);
        listview3 = (ListView) findViewById(R.id.listview3);
        listview3.setAdapter(adapter3);

        ////////////////////////////////////////////////////////////탭호스트////////////
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        // 첫 번째 Tab
        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("관심있는");
        tabHost.addTab(ts1);

        // 두 번째 Tab
        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("인기있는");
        tabHost.addTab(ts2);

        // 세 번째 Tab
        TabHost.TabSpec ts3 = tabHost.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("새로운");
        tabHost.addTab(ts3);

        tabHost.setCurrentTab(0);

        ///////////////////////////////생성된 listview 에 클릭 이벤트 핸들러 정의//////////////////////
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //리스트뷰 추가 -> 서버와 연결해서 데이터를 받아오는 부분//////////////////////
        adapter1.addItem("해시네임");
        adapter1.addItem("해시네임");
        adapter1.addItem("해시네임");

        adapter2.addItem("인기있는");
        adapter2.addItem("popular");

        adapter3.addItem("새로운");

    }
        //리스트뷰에서 탭 선택시, 해당 탭으로 화면 전환/////////////////////////
        @Override
        public void onListBtnClick(String name){
            Intent intent = new Intent(getApplicationContext(),HashTagActivity.class);
            intent.putExtra("name",name); //탭 name 데이터를 넘겨준다

            startActivity(intent);
    }
}