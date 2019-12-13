package com.example.gamsung.controller;

import android.content.Context;

public class UserController {

    Context context;

    public UserController(Context context) {
        this.context = context;
    }

    /*
    public void signUp(File file, String json) {
        Call<FileInfo> response = NetRetrofit.getInstance().getNetRetrofitInterface().signUp(file, json);

        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    public void login(LoginDto loginDto) {
        Call<String> response = NetRetrofit.getInstance().getNetRetrofitInterface().login(loginDto);

        response.enqueue(new Callback<LoginDto>() {
            @Override
            public void onResponse(Call<LoginDto> call, Response<LoginDto> response) {
                if(response.isSuccessful()){
                    //String result = response.body();

                   //return result;

                }
            }

            @Override
            public void onFailure(Call<LoginDto> call, Throwable t) {

            }
        });
    }

     */





}

