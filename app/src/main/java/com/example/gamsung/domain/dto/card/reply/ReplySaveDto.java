package com.example.gamsung.domain.dto.card.reply;

public class ReplySaveDto {

    private String identity;
    private Long cno;
    private String content;
    private int fontsize;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

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
}
