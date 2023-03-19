package com.todowebapp.util;

import com.todowebapp.enums.ResponseEnum;

public class FailResponseUtil extends CommonResponseUtil{

    private static final String RESULT_CODE = ResponseEnum.FAIL.getValue();

    public FailResponseUtil(){
        super(RESULT_CODE);
    }

    public FailResponseUtil(String resultMsg){
        super(RESULT_CODE, resultMsg, null);
    }

    public FailResponseUtil(String resultCode, String resultMsg){
        super(resultCode, resultMsg, null);
    }
}
