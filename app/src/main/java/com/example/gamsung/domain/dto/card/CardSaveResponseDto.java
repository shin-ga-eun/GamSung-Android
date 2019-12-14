package com.example.gamsung.domain.dto.card;

public class CardSaveResponseDto {

    private Long cno;

    public CardSaveResponseDto(Long cno) {
        this.cno = cno;
    }

    public Long getCno() {
        return cno;
    }

    public void setCno(Long cno) {
        this.cno = cno;
    }
}
