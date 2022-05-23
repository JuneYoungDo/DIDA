package com.service.dida.utils.exceptionConfig;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public enum BaseResponseStatus {
    EMPTY_JWT(400,"Jwt가 없습니다.",100),
    EMPTY_REFRESH(400,"Refresh Token이 없습니다.",101),
    INVALID_JWT(400,"Jwt가 유효하지 않습니다.",102),
    INVALID_REFRESH(400,"Refresh Token이 유효하지 않습니다.",103),
    INVALID_KAKAO_ID_TOKEN(400,"유효하지 않은 카카오 ID Token 입니다.",104),
    INVALID_APPLE_ID_TOKEN(400,"유효하지 않은 애플 ID Token 입니다.",105),
    FAILED_TO_APPLE_LOGIN(400,"애플 로그인에 실패하였습니다.",106),
    FAILED_TO_FIND_AVAILABLE_RSA(400,"암호키를 찾지 못하였습니다.",117),
    ;


    private final Timestamp timestamp;
    private final int status;
    private String message;
    private final int code;

    BaseResponseStatus(int status, String message, int code) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
