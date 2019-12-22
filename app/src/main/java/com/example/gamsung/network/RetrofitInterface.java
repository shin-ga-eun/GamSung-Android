package com.example.gamsung.network;

import com.example.gamsung.domain.dto.card.GetCardByIdentityDto;
import com.example.gamsung.domain.dto.card.GetCardByTagDto;
import com.example.gamsung.domain.dto.card.GetCardDto;
import com.example.gamsung.domain.dto.card.reply.GetReplyByCnoDto;
import com.example.gamsung.domain.dto.tag.GetTagDto;
import com.example.gamsung.domain.dto.tag.TagSaveDto;
import com.example.gamsung.domain.dto.user.GetProfileDto;
import com.example.gamsung.domain.dto.user.GetUserNameDto;
import com.example.gamsung.domain.dto.user.LoginDto;
import com.example.gamsung.domain.dto.user.LoginResponseDto;
import com.example.gamsung.domain.dto.user.UserUpdateDto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitInterface {


    //회원가입 -유저정보, 유저프로필이미지
    @Multipart
    @POST("/signUp")
    Call<ResponseBody> signUp(@Part MultipartBody.Part imageFile, @Part("signUpDto") RequestBody json);

    //회원정보수정 -유저정보수정
    @POST("/userUpdate")
    Call<Void> userUpdate(@Body UserUpdateDto userUpdateDto);

    //회원정보수정 -유저이미지수정
    @Multipart
    @POST("/userImageUpdate")
    Call<ResponseBody> userImageUpdate(@Part MultipartBody.Part imageFile, @Part("userImageUpdateDto") RequestBody json);

    //로그인
    @POST("/login")
    Call<LoginResponseDto> login(@Body LoginDto loginDto);

    //마이프로필 유저정보 출력 -identity
    @GET("/getProfile/{identity}")
    Call<GetProfileDto> getProfile(@Path("identity") String identity);

    //카드 저장
    @Multipart
    @POST("/saveCard")
    Call<ResponseBody> saveCard(@Part MultipartBody.Part imageFile, @Part("cardSaveDto") RequestBody json);

    //카드 저장 후, 태그 저장 시 사용 -> 카드저장에서 받은 cno로 저장
    @POST("/saveTag")
    Call<Void> saveTag(@Body TagSaveDto tagSaveDto);

    //tagName 상세보기 (해당 태그에 해당하는 카드 리스트 출력)
    @GET("/getCardByTag/{tagname}")
    Call<List<GetCardByTagDto>> getCardByTag(@Path("tagname") String tagname);

    //마이프로필 카드 리스트 출력 -identity
    @GET("/getProfileCard/{identity}")
    Call<List<GetCardByIdentityDto>> getCardByIdentity(@Path("identity") String identity);

    //하나의 카드 상세보기 -cno
    @GET("/getCard/{cno}")
    Call<GetCardDto> getCardByCno(@Path("cno") Long cno);

    //메인홈 -인기있는 탭 리스트
    @GET("/getPopular")
    Call<List<GetTagDto>> getPopular();

    //메인홈 -새로운 탭 리스트
    @GET("/getNew")
    Call<List<GetTagDto>> getNew();

    //댓글 저장
    @Multipart
    @POST("/saveReply")
    Call<ResponseBody> saveReply(@Part MultipartBody.Part imageFile, @Part("replySaveDto") RequestBody json);

    //댓글 리스트 출력 -cno
    @GET("/getReply/{cno}")
    Call<List<GetReplyByCnoDto>> getReply(@Path("cno") Long cno);

    //유저 검색 -identity
    @GET("/getSearchKeyword/{keyword}")
    Call<List<GetUserNameDto>> getSearchKeyword(@Path("keyword") String keyword);


}
