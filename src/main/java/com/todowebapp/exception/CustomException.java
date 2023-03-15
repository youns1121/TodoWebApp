package com.todowebapp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 1223367963286140690L;

    private int status;

    private String message;

    private HttpStatus httpErrorStatus;



    public CustomException(int status, String message, HttpStatus httpErrorStatus) {
        super(message);
        this.status = status;
        this.message = message;
        this.httpErrorStatus = httpErrorStatus;
    }
}
