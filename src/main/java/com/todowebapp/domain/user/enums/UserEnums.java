package com.todowebapp.domain.user.enums;

import com.todowebapp.enums.EnumModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserEnums implements EnumModel {

    OK("0000", "OK"),
    FAIL("9999", "FAIL"),
    USERNAME_ALREADY_EXISTS("1000","이미 사용된 사용자 이름입니다."),
    USERNAME_NOT_FOUND("1001", "사용자 이름을 찾을 수 없습니다."),
    INVALID_PASSWORD("2000", "잘못된 비밀번호 입니다.")
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
