package com.todowebapp.handler;

import com.todowebapp.util.CommonResponseUtil;
import com.todowebapp.util.FailResponseUtil;
import com.todowebapp.util.SuccessResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {

    /** 완료 응답 **/
    public <T> ResponseEntity<CommonResponseUtil<T>> ok() {
        return makeResponseMessage(new SuccessResponseUtil());
    }

    /** 실패 응답 **/
    public <T> ResponseEntity<CommonResponseUtil<T>> fail() {
        return makeResponseMessage(new FailResponseUtil());
    }

    /** 실패 및 상태 코드, 메세지 응답 **/
    public <T> ResponseEntity<CommonResponseUtil<T>> fail(String resultCode, String resultMsg) {
        return makeResponseMessage(new FailResponseUtil(resultCode, resultMsg));
    }


    private <T> ResponseEntity<CommonResponseUtil<T>> makeResponseMessage(CommonResponseUtil CommonResponse){
        return new ResponseEntity<>(CommonResponse, HttpStatus.OK);
    }
}