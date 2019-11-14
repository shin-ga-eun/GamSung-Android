package com.example.gamsung;

public class MyProfileGridViewItem {

    private String content; //카드내용
    private int fontSize; //폰트크기
//    private int CardNum; //카드게시글넘버
    private int img; //카드이미지

    public void setContent(String content){

        this.content = content;
    }

    public String getContent(){

        return this.content;
    }

    public void setFontSize(int fontSize){

        this.fontSize = fontSize;
    }

    public int getFontSize (){

        return this.fontSize;
    }

    public void setImg(int img){

        this.img = img;
    }

    public int getImg (){

        return this.img;
    }
}