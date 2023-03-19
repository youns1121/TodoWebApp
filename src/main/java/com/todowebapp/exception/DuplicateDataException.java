package com.todowebapp.exception;

import org.springframework.http.HttpStatus;

public class DuplicateDataException extends CustomException{

    private static final long serialVersionUID = -281727742745995814L;

    public DuplicateDataException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public DuplicateDataException(int status, String message, HttpStatus httpErrorStatus) {
        super(status, message, httpErrorStatus);
    }
}
