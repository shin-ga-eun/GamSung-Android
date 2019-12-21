package com.example.gamsung.MainHome.HashSearch.HashList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.gamsung.CardActivity;
import com.example.gamsung.R;
import com.example.gamsung.domain.dto.card.GetCardByTagDto;
import com.example.gamsung.network.NetRetrofit;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HashTagActivity extends AppCompatActivity {

    TextView textHashTagName;
    GridView gridview;
    Button btnMainHome;
    String tagname;
    HashTagGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtag);

        Intent inIntent = getIntent();
        tagname = inIntent.getStringExtra("name");

        textHashTagName = (TextView)findViewById(R.id.textHashTagName);
        textHashTagName.setText(tagname);

        ////////게시물 그리드뷰 /////////////////////////////////////////////////////////////////////
        gridview = (GridView)findViewById(R.id.gridview);
        adapter = new HashTagGridViewAdapter(this);
        gridview.setAdapter(adapter);

        Call<List<GetCardByTagDto>> response= NetRetrofit.getInstance().getNetRetrofitInterface().getCardByTag(tagname);
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
