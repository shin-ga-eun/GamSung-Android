package com.example.gamsung.controller;

import android.content.Context;

import com.example.gamsung.domain.dto.tag.TagSaveDto;
import com.example.gamsung.network.NetRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardController {

    Context context;

    public CardController(Context context){
        this.context=context;
    }

    public void saveTag(TagSaveDto tagSaveDto){
        Call<Void> response= NetRetrofit.getInstance().getNetRetrofitInterface().saveTag(tagSaveDto);
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
