package com.example.gamsung.MainHome.HashSearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.gamsung.MainHome.HashSearch.HashList.HashTagActivity;
import com.example.gamsung.MainHome.MainHomeActivity;
import com.example.gamsung.R;

import androidx.appcompat.app.AppCompatActivity;

//해시태그검색기능 액티비티

public class HashSearchActivity extends AppCompatActivity implements HashSearchListViewAdapter.ListBtnClickListener {

    EditText edtHash;
    Button btnMainHome, btnKeyword;

    String keywordData; //검색키워드

    HashSearchListViewAdapter adapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashsearch);

        edtHash = (EditText) findViewById(R.id.edtHash);
        btnKeyword = (Button) findViewById(R.id.btnKeyword);
        btnMainHome = (Button) findViewById(R.id.btnMainHome);

        //메인홈으로 이동버튼
        btnMainHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), MainHomeActivity.class);
                startActivity(intent); //다음화면으로 넘어감
                finish();
            }
        });

        //서버에서 데이터를 가져와
        btnKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywordData = edtHash.getText().toString();

                Log.d("keywordData 값", keywordData); //로그
            }
        });

        //////////리스트뷰 어댑터 참조//////////////////////////////////////////////
        adapter = new HashSearchListViewAdapter(this);
        listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(adapter);

        adapter.addItem("임시데이터");
        adapter.addItem("임시데이터");
        adapter.addItem("임시데이터");
        adapter.addItem("임시데이터");


        ///////////////////////////////생성된 listview 에 클릭 이벤트 핸들러 정의/////
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

        @Override
        public void onListBtnClick(String name){
            Intent intent = new Intent(getApplicationContext(), HashTagActivity.class); //어디로이동?????????????????????????????
            intent.putExtra("name",name); //탭 name 데이터를 넘겨준다

            startActivity(intent);
        }
    }


