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
    TOKEN_REQUIRED("AU0007", "토큰이 필요합니다."),

    /**
     * Member Related Errors
     */
    MEMBER_NOT_FOUND("MB0001", "존재하지 않는 회원입니다."),
    MEMBER_ALREADY_EXISTS("MB0002", "이미 존재하는 회원입니다."),

    /**
     * House Related Errors
     */
    HOUSE_NOT_FOUND("HS0001", "존재하지 않는 주택입니다."),
    HOUSE_IMAGE_NOT_FOUND("HS0002", "존재하지 않는 주택 이미지입니다."),

    /**
     * Post Related Errors
     */
    POST_NOT_FOUND("PT0001", "존재하지 않는 공고입니다."),
    POST_INCOME_LIMIT_PERSON_EXCEEDED("PT0002", "공고에 등록된 소득기준 인원 수가 초과되었습니다."),
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
