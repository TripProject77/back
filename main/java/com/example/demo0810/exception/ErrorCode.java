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
    NOT_CORRECT_PASSWORD("003_NOT_CORRECT_PASSWORD", "비밀번호가 맞지 않습니다.");

    private final String errorCode;

    private final String message;
}
