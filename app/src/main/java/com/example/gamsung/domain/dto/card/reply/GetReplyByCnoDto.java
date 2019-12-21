package com.example.gamsung.domain.dto.card.reply;

public class GetReplyByCnoDto {

    private Long rno;
    private String identity;
    private String content;
    private int fontsize;
    private String regDate;
    private String imageUrl;

    public Long getRno() {
        return rno;
    }

    public void setRno(Long rno) {
        this.rno = rno;
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

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
