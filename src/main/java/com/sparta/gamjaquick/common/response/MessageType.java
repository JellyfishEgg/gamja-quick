package com.sparta.gamjaquick.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {

    RETRIEVE("조회가 완료되었습니다."),
    CREATE("생성이 완료되었습니다."),
    UPDATE("수정이 완료되었습니다."),
    DELETE("삭제가 완료되었습니다."),
    APPROVE("승인이 완료되었습니다.");

    private final String message;

}

