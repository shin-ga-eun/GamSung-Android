package com.example.gamsung.domain.dto.user;

public class LoginResponseDto {

    private String identity;

    public LoginResponseDto(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
