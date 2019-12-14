package com.example.gamsung.domain.dto.card;

public class CardSaveDto {

    private String identity;
    private String content;
    private int fontsize;

    public CardSaveDto(String identity, String content, int fontsize) {
        this.identity = identity;
        this.content = content;
        this.fontsize = fontsize;
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
}
