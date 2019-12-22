package com.example.gamsung.Main.UserSearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.gamsung.Main.MainHome.MainHomeActivity;
import com.example.gamsung.Main.MyProfile.MyProfileActivity;
import com.example.gamsung.R;
import com.example.gamsung.domain.dto.user.GetUserNameDto;
import com.example.gamsung.network.NetRetrofit;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//해시태그검색기능 액티비티

public class UserSearchActivity extends AppCompatActivity implements UserSearchListViewAdapter.ListBtnClickListener {

    EditText edtUser;
    Button btnMainHome, btnKeyword;

    String keywordData; //검색키워드

    UserSearchListViewAdapter adapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersearch);

        edtUser = (EditText) findViewById(R.id.edtUser);
        btnKeyword = (Button) findViewById(R.id.btnKeyword);
        btnMainHome = (Button) findViewById(R.id.btnMainHome);

        //////////리스트뷰 어댑터 참조//////////////////////////////////////////////
        adapter = new UserSearchListViewAdapter(this);
        listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(adapter);

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
                keywordData = edtUser.getText().toString();
                Log.d("keywordData 값", keywordData); //로그

                //서버연동
                Call<List<GetUserNameDto>> response = NetRetrofit.getInstance().getNetRetrofitInterface().getSearchKeyword(keywordData);
                response.enqueue(new Callback<List<GetUserNameDto>>() {
                    @Override
                    public void onResponse(Call<List<GetUserNameDto>> call, Response<List<GetUserNameDto>> response) {
                        if (response.isSuccessful()) {
                            Log.d("userSearch in Controller", "여기 들어와써여");
                            List<GetUserNameDto> resource = response.body();

                            for (GetUserNameDto getUserNameDto : resource) {
                                adapter.addItem(getUserNameDto.getIdentity());
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<GetUserNameDto>> call, Throwable t) {

                    }

                });

                adapter.deleteItem();
                adapter.notifyDataSetChanged();

            }
        });




        ///////////////////////////////생성된 listview 에 클릭 이벤트 핸들러 정의 -> name 넘겨줌/////
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onListBtnClick(String name){
        Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
        intent.putExtra("nickname",name); //탭 name 데이터를 넘겨준다
        startActivity(intent);
        finish();
    }
}


