package com.todowebapp.handler;

import com.todowebapp.enums.ResponseEnum;
import com.todowebapp.exception.CustomException;
import com.todowebapp.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public final String STATUS = "status";
    public final String MESSAGE = "message";
    public final String ERROR = "error";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> response = new HashMap<>();
        response.put(STATUS, status.value());
        response.put(ERROR, status.name());

        if(ex.getBindingResult().getFieldErrorCount() > 0) {
            String msg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
            msg = messageSource.getMessage(msg, null, Locale.KOREA);
            response.put(MESSAGE, msg);
        } else {
            response.put(MESSAGE, ResponseEnum.INVALID_ARGUMENT.getValue());
        }

        return new ResponseEntity<>(response, status);

    }

    @Override
    @ExceptionHandler({CustomException.class, DataNotFoundException.class})
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        CustomException customException = new CustomException(
                status.value()
                , ex.getMessage().equals(ResponseEnum.FAIL.getValue()) ? ex.getMessage() : ResponseEnum.FAIL.getValue()
                , status);


        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(STATUS, customException.getStatus());
        errorResponse.put(MESSAGE, customException.getMessage());
        errorResponse.put(ERROR, customException.getHttpErrorStatus());

        return new ResponseEntity<>(errorResponse, status);
    }

    public ResponseEntity<Object> handlerCustomException(Exception ex) {

        CustomException customException = (CustomException) ex;

        Map<String, Object> response = new HashMap<>();
        response.put(STATUS, customException.getStatus());
        response.put(MESSAGE, customException.getMessage());
        response.put(ERROR, customException.getHttpErrorStatus());

        return new ResponseEntity<>(response, customException.getHttpErrorStatus());
    }
}
