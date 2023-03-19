package com.todowebapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum implements EnumModel {

    OK("0000", "OK"),
    FAIL("9999", "FAIL"),

    INVALID_ARGUMENT("9001", "잘못된 요청값 입니다"),
    ;

    private final String key;
    private final String value;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
