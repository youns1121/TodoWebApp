package com.todowebapp.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends CustomException{

    private static final long serialVersionUID = -7617152737996929345L;

    public DataNotFoundException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public DataNotFoundException(int status, String message, HttpStatus httpErrorStatus) {
        super(status, message, httpErrorStatus);
    }
}
