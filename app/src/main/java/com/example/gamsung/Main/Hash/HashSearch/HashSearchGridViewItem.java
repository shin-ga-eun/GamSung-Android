package com.example.gamsung.Main.Hash.HashSearch;

public class HashSearchGridViewItem {

    private String imageUrl; //카드이미지
    private String content; //카드내용
    private int fontsize; //폰트크기
    private Long cno; //카드게시글넘버


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Long getCno() {
        return cno;
    }

    public void setCno(Long cno) {
        this.cno = cno;
    }
}