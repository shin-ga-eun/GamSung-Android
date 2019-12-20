package com.example.gamsung.domain.dto.user;

public class UserUpdateDto {

    private String identity;
    private String profileText;

    public UserUpdateDto(String identity, String profileText) {
        this.identity = identity;
        this.profileText = profileText;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getProfileText() {
        return profileText;
    }

    public void setProfileText(String profileText) {
        this.profileText = profileText;
    }
}
