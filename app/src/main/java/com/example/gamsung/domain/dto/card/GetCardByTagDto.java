package com.example.gamsung.domain.dto.card;

public class GetCardByTagDto {
    private Long cno;
    private String content;
    private int fontsize;
    private String imageUrl;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
