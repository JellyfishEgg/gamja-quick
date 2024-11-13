package com.sparta.gamjaquick.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "E-001", "입력값 검증에 실패했습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E-002", "서버에 오류가 발생했습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E-003", "지원하지 않는 HTTP Method 요청입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "E-004", "요청한 리소스를 찾을 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "E-005", "인증에 실패했습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "E-006", "접근 권한이 없습니다."),

    // 카테고리 관련
    CATEGORY_ALREADY_EXISTS(HttpStatus.CONFLICT, "CAT-001", "이미 존재하는 카테고리입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CAT-002", "존재하지 않는 카테고리입니다."),
    CATEGORY_ALREADY_DELETED(HttpStatus.CONFLICT, "CAT-003", "이미 삭제된 카테고리입니다."),

    // 가게 관련
    STORE_ALREADY_EXISTS(HttpStatus.CONFLICT, "ST-001", "해당 주소에 이미 존재하는 가게입니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "ST-002", "존재하지 않는 가게입니다."),
    STORE_APPROVAL_PENDING(HttpStatus.CONFLICT, "ST-003", "승인 대기 중인 가게입니다."),
    STORE_APPROVAL_REJECTED(HttpStatus.CONFLICT, "ST-004", "승인 거부된 가게입니다."),
    STORE_INVALID_ADDRESS(HttpStatus.BAD_REQUEST, "ST-005", "유효하지 않은 주소입니다."),
    STORE_ALREADY_APPROVED(HttpStatus.CONFLICT, "ST-006", "이미 승인된 가게입니다."),

    //메뉴 관련
    MENU_ALREADY_DELETED(HttpStatus.CONFLICT, "MEN-001", "이미 삭제된 메뉴입니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "MEN-002","해당 메뉴를 찾을 수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
