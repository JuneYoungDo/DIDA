package com.service.dida.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity customException(BaseException exception) {
        return new ResponseEntity(new BaseResponse(exception.getStatus()), HttpStatus.valueOf(exception.getStatus().getStatus()));
    }
}
