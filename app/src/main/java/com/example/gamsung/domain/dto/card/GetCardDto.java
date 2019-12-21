package com.example.gamsung.domain.dto.card;

public class GetCardDto {

    private Long cno;
    private String identity;
    private String content;
    private int fontsize;
    private String imageUrl;
    private String regDate;
    private int heart;

    public Long getCno() {
        return cno;
    }

    public void setCno(Long cno) {
        this.cno = cno;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }
}

