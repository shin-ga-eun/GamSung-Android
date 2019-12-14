package com.example.gamsung.network;

import com.example.gamsung.domain.dto.tag.TagSaveDto;
import com.example.gamsung.domain.dto.user.LoginDto;
import com.example.gamsung.domain.dto.user.LoginResponseDto;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {


    @Multipart
    @POST("/signUp")
    Call<ResponseBody> signUp(@Part MultipartBody.Part imageFile, @Part("signUpDto") RequestBody json);

    @POST("/login")
    Call<LoginResponseDto> login(@Body LoginDto loginDto);

    @Multipart
    @POST("/saveCard")
    Call<ResponseBody> saveCard(@Part MultipartBody.Part imageFile, @Part("cardSaveDto") RequestBody json);

    @POST("/saveTag")
    Call<Void> saveTag(@Body TagSaveDto tagSaveDto);


}
