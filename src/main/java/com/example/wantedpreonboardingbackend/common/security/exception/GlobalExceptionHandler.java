package com.example.wantedpreonboardingbackend.common.security.exception;

import com.example.wantedpreonboardingbackend.common.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "GlobalExceptionHandler")
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO<Void>> handleUserAlreadyExistsException(
        MemberAlreadyExistsException e){
        log.warn(e.getMessage());
        return new ResponseEntity<>(ResponseDTO.ofError(e.getMessage()), HttpStatus.CONFLICT);
    }

}
