package com.example.gamsung;

public class CardRecyclerItem {

    private int img; //카드이미지
    private String content; //카드내용
    private int fontSize; //폰트크기
    private String time; //올린날짜
//    private int CardNum; //카드게시글넘버

    public CardRecyclerItem(int img, String content, int fontSize, String time){
        this.img = img;
        this.content = content;
        this.fontSize = fontSize;
        this.time = time;
    }

    public void setImg(int img){
        this.img = img;
    }

    public int getImg (){
        return this.img;
    }

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

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){
        return this.time;
    }

}

