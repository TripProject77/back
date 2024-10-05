package com.example.demo0810.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.PrimitiveIterator;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    UNKNOWN("000_UNKNOWN", "알 수 없는 에러가 발생했습니다."),


    DUPLICATED_ID_FAILED("001_DUPLICATED_ID", "중복된 ID입니다."),

    NOT_EXIST_USER("002_NOT_EXIST_USER", "존재하지 않는 사용자입니다."),

    NOT_EXIST_POST("003_NOT_EXIST_POST", "존재하지 않는 게시물입니다."),

    NOT_EXIST_COMMENT("003_NOT_EXIST_COMMENT", "존재하지 않는 댓글입니다."),

    POST_DELETE_ERROR("POST_DELETE_ERROR", "게시글 삭제 중 에러"),

    NOT_CORRECT_PASSWORD("004_NOT_CORRECT_PASSWORD", "비밀번호가 맞지 않습니다."),

    UNATHORIZATION("006_UNATHORIZATION", "인증되지 않아습니다."),

    INVALID_DATE_FORMAT("INVALID_DATE_FORMAT", "잘못된 날짜 변환");

    private final String errorCode;

    private final String message;
}
