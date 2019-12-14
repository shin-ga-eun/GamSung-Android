package com.example.gamsung.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;

public class Card implements Serializable {

    @SerializedName("cno")
    private Long cno;
    @SerializedName("content")
    private String content;
    @SerializedName("fontsize")
    private int fontsize;
    @SerializedName("regDate")
    private LocalDate regDate;
    @SerializedName("heart")
    private int heart;

    @SerializedName("user")
    private User user;

    public Long getCno() {
        return cno;
    }

    public void setCno(Long cno) {
        this.cno = cno;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
