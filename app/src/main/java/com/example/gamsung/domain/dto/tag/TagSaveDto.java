package com.example.gamsung.domain.dto.tag;

public class TagSaveDto {

    private String tagname;
    private Long cno;

    public TagSaveDto(String tagname, Long cno) {
        this.tagname = tagname;
        this.cno = cno;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Long getCno() {
        return cno;
    }

    public void setCno(Long cno) {
        this.cno = cno;
    }
}
