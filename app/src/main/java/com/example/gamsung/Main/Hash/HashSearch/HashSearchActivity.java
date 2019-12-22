package com.example.gamsung.Main.Hash.HashSearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.gamsung.Card.CardActivity;
import com.example.gamsung.Main.MainHome.MainHomeActivity;
import com.example.gamsung.R;
import com.example.gamsung.domain.dto.card.GetCardByTagDto;
import com.example.gamsung.network.NetRetrofit;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//해시태그검색기능 액티비티

public class HashSearchActivity extends AppCompatActivity {

    EditText edtHash;
    Button btnMainHome, btnKeyword;

    String keywordData; //검색키워드

    HashSearchGridViewAdapter adapter;
    GridView gridview;

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

        //////////리스트뷰 어댑터 참조//////////////////////////////////////////////
        adapter = new HashSearchGridViewAdapter(this);
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(adapter);


        //서버에서 데이터를 가져와
        btnKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywordData = edtHash.getText().toString();
                Log.d("keywordData 값", keywordData); //로그

                Call<List<GetCardByTagDto>> response= NetRetrofit.getInstance().getNetRetrofitInterface().getSearchTag(keywordData);
                response.enqueue(new Callback<List<GetCardByTagDto>>() {
                    @Override
                    public void onResponse(Call<List<GetCardByTagDto>> call, Response<List<GetCardByTagDto>> response) {
                        if(response.isSuccessful()) {
                            Log.d("getCardByTagDto in cardController", "여기 들어와써여");
                            List<GetCardByTagDto> resource = response.body();

                            for(GetCardByTagDto getCardByTagDto: resource){
                                adapter.addItem(getCardByTagDto.getCno(), getCardByTagDto.getContent(), getCardByTagDto.getImageUrl(), getCardByTagDto.getFontsize());
                                Log.d("getCardByTagDto",getCardByTagDto.getCno().toString());
                                Log.d("getCardByTagDto",getCardByTagDto.getContent());
                                Log.d("getCardByTagDto",getCardByTagDto.getImageUrl());
                                Log.d("getCardByTagDto", ""+getCardByTagDto.getFontsize());
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }
                    @Override
                    public void onFailure(Call<List<GetCardByTagDto>> call, Throwable t) {

                    }

                });

                adapter.deleteItem();
                adapter.notifyDataSetChanged();

            }
        });


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Long cno = Long.valueOf(String.valueOf(gridview.getAdapter().getItem(position)));
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                intent.putExtra("cno",cno);
                startActivity(intent);
            }
        });
    }


    }


