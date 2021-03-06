package com.service.dida.exception;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public enum BaseResponseStatus {
    EMPTY_JWT(400, "Jwt가 없습니다.", 100),
    EMPTY_REFRESH(400, "Refresh Token이 없습니다.", 101),
    INVALID_JWT(400, "Jwt가 유효하지 않습니다.", 102),
    INVALID_REFRESH(400, "Refresh Token이 유효하지 않습니다.", 103),
    INVALID_KAKAO_ID_TOKEN(400, "유효하지 않은 카카오 ID Token 입니다.", 104),
    INVALID_APPLE_ID_TOKEN(400, "유효하지 않은 애플 ID Token 입니다.", 105),
    FAILED_TO_APPLE_LOGIN(400, "애플 로그인에 실패하였습니다.", 106),
    FAILED_TO_FIND_AVAILABLE_RSA(400, "암호키를 찾지 못하였습니다.", 107),
    INVALID_PATH(400, "유효하지 않은 경로입니다.", 108),
    INVALID_NICKNAME(400, "사용할 수 없는 닉네임 입니다.", 109),
    INVALID_EMAIL(400, "이미 사용중인 이메일 입니다.", 110),
    INVALID_USER(400,"올바르지 않은 사용자 입니다.",111),
    EMPTY_REFRESH_TOKEN(400,"refresh token 이 없습니다.",112),
    INVALID_TOKEN(400,"유효하지 않은 토큰입니다.",113),
    NEED_WALLET_OR_WRONG_PWD(400,"지갑이 없거나 비밀번호가 올바르지 않습니다.",114),
    INVALID_CARD_ID(400,"유효하지 않은 NFT 입니다.",115),
    ALREADY_IN_MARKET(400,"이미 판매중입니다.",116),
    FAILED_TO_CREAT_ACCOUNT(400,"지갑 생성에 실패하였습니다.",117),




    INTERNAL_SERVER_ERROR(500,"외부 API 동작에 실패하였습니다.",499)
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
