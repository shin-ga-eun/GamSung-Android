package com.example.gamsung.network;

import com.example.gamsung.domain.dto.user.SignUpDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {


    @POST("signup")
    Call<Void> signUp(@Body SignUpDto signUpDto);

}
