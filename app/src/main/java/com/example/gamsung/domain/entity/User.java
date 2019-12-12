package com.example.gamsung.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("uno")
    private Long uno;
    @SerializedName("identity")
    private String identity;
    @SerializedName("password")
    private String password;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("profileText")
    private String profileText;
    @SerializedName("today")
    private String today;
    @SerializedName("total")
    private String total;

}
