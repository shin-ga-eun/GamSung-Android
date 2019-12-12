package com.example.gamsung.controller;

import android.content.Context;

import com.example.gamsung.domain.dto.user.SignUpDto;
import com.example.gamsung.network.NetRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserController {

    Context context;

    public UserController(Context context) {
        this.context = context;
    }

    public void signUp(SignUpDto signUpDto) {
        Call<Void> response = NetRetrofit.getInstance().getNetRetrofitInterface().signUp(signUpDto);

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
