package com.service.dida.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.text.ParseException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity customException(BaseException exception) {
        return new ResponseEntity(new BaseResponse(exception.getStatus()), HttpStatus.valueOf(exception.getStatus().getStatus()));
    }

    @ExceptionHandler({IOException.class, ParseException.class,InterruptedException.class})
    public ResponseEntity outException() {
        BaseException exception = new BaseException(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity(new BaseResponse(exception.getStatus()),HttpStatus.valueOf(exception.getStatus().getStatus()));
    }
}
