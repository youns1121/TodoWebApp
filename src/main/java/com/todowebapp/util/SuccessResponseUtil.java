package com.todowebapp.util;

import com.todowebapp.enums.ResponseEnum;

public class SuccessResponseUtil extends CommonResponseUtil {

    private static final String RESULT_CODE = ResponseEnum.OK.getValue();

    public SuccessResponseUtil() {
        super(RESULT_CODE);
    }
    public SuccessResponseUtil(String resultMsg) {
        super(RESULT_CODE, resultMsg, null);
    }
}