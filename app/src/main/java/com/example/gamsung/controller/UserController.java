package com.example.gamsung.controller;

import android.content.Context;
import android.util.Log;

import com.example.gamsung.domain.dto.user.LoginDto;
import com.example.gamsung.domain.dto.user.LoginResponseDto;
import com.example.gamsung.network.NetRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gamsung.LoginActivity.loginCheck;

public class UserController {

    Context context;

    public UserController(Context context) {
        this.context = context;
    }


    public void login(LoginDto loginDto) {
        Call<LoginResponseDto> response = NetRetrofit.getInstance().getNetRetrofitInterface().login(loginDto);

        response.enqueue(new Callback<LoginResponseDto>() {
            @Override
            public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
                if (response.isSuccessful()) {
                    loginCheck = response.body().getIdentity();
                    Log.d("login check", loginCheck);
                }else{
                    loginCheck = "login fail";
                    Log.d("login check", loginCheck);
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDto> call, Throwable t) {

            }
        });

    }

}








