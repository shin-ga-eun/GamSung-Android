package com.example.gamsung.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {


    @Multipart
    @POST("/signUp")
    Call<ResponseBody> signUp(@Part MultipartBody.Part imageFile, @Part("signUpDto") RequestBody json);

   // @POST("login")
    //Call<void> login(@Body LoginDto loginDto);

}
