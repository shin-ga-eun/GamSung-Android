package com.example.gamsung.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;

public class Tag implements Serializable {

    @SerializedName("tno")
    private Long tno;
    @SerializedName("tagname")
    private String tagname;
    @SerializedName("regDate")
    private LocalDate regDate;

    @SerializedName("cno")
    private Long cno;

    public Tag(Long tno, String tagname, LocalDate regDate, Long cno) {
        this.tno = tno;
        this.tagname = tagname;
        this.regDate = regDate;
        this.cno = cno;
    }

    public Long getTno() {
        return tno;
    }

    public void setTno(Long tno) {
        this.tno = tno;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public Long getCno() {
        return cno;
    }

    public void setCno(Long cno) {
        this.cno = cno;
    }
}
