package com.example.gamsung.domain.dto.user;

public class SignUpDto {

    private String identity; //아이디

    private String password; //비밀번호
    private String nickname; //닉네임

    public SignUpDto(String identity, String password, String nickname) {
        this.identity = identity;
        this.password = password;
        this.nickname = nickname;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
