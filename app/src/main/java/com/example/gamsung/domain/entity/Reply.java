package com.example.gamsung.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Reply {

    @SerializedName("rno")
    private Long rno;
    @SerializedName("content")
    private String content;
    @SerializedName("fontsize")
    private int fontsize;
    @SerializedName("regDate")
    private LocalDate regDate;
    @SerializedName("identity")
    private String identity;

    @SerializedName("card")
    private Card card;


    public Long getRno() {
        return rno;
    }

    public void setRno(Long rno) {
        this.rno = rno;
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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
