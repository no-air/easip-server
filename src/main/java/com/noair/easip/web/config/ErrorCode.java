package com.noair.easip.web.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * Common Errors
     */
    UNKNOWN_SERVER_ERROR("CM0001", "Unknown Server Error"),
    INVALID_INPUT_VALUE("CM0002", "Invalid Input Value"),
    METHOD_NOT_ALLOWED("CM0003", "Method Not Allowed"),

    /**
     * APP Version Related Error
     */
    APP_UPDATE_REQUIRED("AV0001", "Update Required"),
    APP_KEY_NOT_FOUND("AV0002", "App Key Not Found"),

    /**
     * Auth Related Errors
     */
    AUTHENTICATION_FAILED("AU0001", "인증에 실패했습니다."),
    TOKEN_AUTHENTICATION_FAILED("AU0002", "토큰을 통한 인증에 실패했습니다."),
    AUTHORIZATION_FAILED("AU0003", "권한이 없습니다."),
    TOKEN_INVALID("AU0004", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED("AU0005", "토큰이 만료됐습니다."),
    REFRESH_TOKEN_INVALID("AU0006", "유효하지 않은 리프레쉬 토큰입니다."),

    /**
     * Member Related Errors
     */
    MEMBER_NOT_FOUND("MB0001", "찾을 수 없는 회원입니다."),
    MEMBER_ALREADY_EXISTS("MB0002", "이미 존재하는 회원입니다."),
    ;


    private final String code;
    private final String message;

    public static ErrorCode fromCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return UNKNOWN_SERVER_ERROR;
    }
}
