package com.example.gamsung.MainHome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.gamsung.LoginActivity;
import com.example.gamsung.MainHome.HashSearch.HashList.HashTagActivity;
import com.example.gamsung.MainHome.HashSearch.HashSearchActivity;
import com.example.gamsung.MainHome.MyProfile.MyProfileActivity;
import com.example.gamsung.MainHome.UserSearch.UserSearchActivity;
import com.example.gamsung.MainHome.Write.WriteActivity;
import com.example.gamsung.R;
import com.example.gamsung.controller.TagController;
import com.example.gamsung.domain.dto.tag.GetTagDto;
import com.example.gamsung.network.NetRetrofit;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainHomeActivity extends AppCompatActivity implements MainHomeListViewAdapter.ListBtnClickListener{

    private GetTagDto getTagDto;
    private TagController tagController;

    Button btnMyProfile;
    Button btnMain, btnSearch, btnCard, btnTimeLine, btnLogout; // 하단버튼목록들
    private SharedPreferences userInfo;
    private SharedPreferences.Editor loginEditor;

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
        btnLogout = (Button) findViewById(R.id.btnLogout);

        userInfo=getSharedPreferences("UserInformation", Activity.MODE_PRIVATE);
        loginEditor = userInfo.edit();

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HashSearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnTimeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserSearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEditor.putString("identity","");
                loginEditor.commit();

                Toast.makeText(getApplicationContext(), "로그아웃 성공", Toast.LENGTH_LONG).show();
                Intent Logout=new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(Logout);
                finish();
            }
        });

        ////////////////////////////////////////////////////////////adapter//////////////
        final MainHomeListViewAdapter adapter1, adapter2;
        final ListView listview1, listview2;

        adapter1 = new MainHomeListViewAdapter(this);
        listview1 = (ListView) findViewById(R.id.listview1);
        listview1.setAdapter(adapter1);

        adapter2 = new MainHomeListViewAdapter(this);
        listview2 = (ListView) findViewById(R.id.listview2);
        listview2.setAdapter(adapter2);


        ////////////////////////////////////////////////////////////탭호스트////////////
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        // 첫 번째 Tab
        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("인기있는");
        tabHost.addTab(ts1);

        // 두 번째 Tab
        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("새로운");
        tabHost.addTab(ts2);


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



        //리스트뷰 추가 -> 서버와 연결해서 데이터를 받아오는 부분//////////////////////
        //getPopular adapter additem
        Call<List<GetTagDto>> response= NetRetrofit.getInstance().getNetRetrofitInterface().getPopular();
        response.enqueue(new Callback<List<GetTagDto>>() {
            @Override
            public void onResponse(Call<List<GetTagDto>> call, Response<List<GetTagDto>> response) {
                if(response.isSuccessful()) {
                    Log.d("getPopular in tagController", "여기 들어와써여");
                    List<GetTagDto> resource = response.body();

                    for(GetTagDto getTagDto: resource){
                        adapter1.addItem(getTagDto.getTagname());
                    }

                }

            }

            @Override
            public void onFailure(Call<List<GetTagDto>> call, Throwable t) {

            }

        });

        //getNew adapter additem
        Call<List<GetTagDto>> response2= NetRetrofit.getInstance().getNetRetrofitInterface().getNew();
        response2.enqueue(new Callback<List<GetTagDto>>() {
            @Override
            public void onResponse(Call<List<GetTagDto>> call, Response<List<GetTagDto>> response) {
                if(response.isSuccessful()) {
                    Log.d("getPopular in tagController", "여기 들어와써여");
                    List<GetTagDto> resource = response.body();

                    for(GetTagDto getTagDto: resource){
                        adapter2.addItem(getTagDto.getTagname());
                    }

                }

            }

            @Override
            public void onFailure(Call<List<GetTagDto>> call, Throwable t) {

            }

        });


    }
    //리스트뷰에서 탭 선택시, 해당 탭으로 화면 전환/////////////////////////
    @Override
    public void onListBtnClick(String name){
        Intent intent = new Intent(getApplicationContext(), HashTagActivity.class);
        intent.putExtra("name",name); //탭 name 데이터를 넘겨준다

        startActivity(intent);
    }
}