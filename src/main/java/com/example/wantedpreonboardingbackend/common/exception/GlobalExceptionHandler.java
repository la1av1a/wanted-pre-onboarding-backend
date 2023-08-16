package com.example.wantedpreonboardingbackend.common.exception;

import com.example.wantedpreonboardingbackend.common.response.ResponseDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j(topic = "GlobalExceptionHandler")
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Void>> handleValidationExceptions(
        MethodArgumentNotValidException e) {
        e.printStackTrace();

        List<String> errorMessages = e.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + " : " + error.getDefaultMessage())
            .collect(Collectors.toList());

        String combinedErrorMessage = String.join(", ", errorMessages);

        return new ResponseEntity<>(new ResponseDTO<>(null, combinedErrorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO<Void>> handleUserAlreadyExistsException(
        MemberAlreadyExistsException e){
        log.warn(e.getMessage());
        return new ResponseEntity<>(ResponseDTO.ofError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> handleMemberNotFoundException(
        MemberNotFoundException e){
        log.warn(e.getMessage());
        return new ResponseEntity<>(ResponseDTO.ofError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoUpdateContentException.class)
    public ResponseEntity<ResponseDTO<Void>> handleNoUpdateContentException(
        NoUpdateContentException e){
        log.warn(e.getMessage());
        return new ResponseEntity<>(ResponseDTO.ofError(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> handleNotFoundException(
        NotFoundException e){
        log.warn(e.getMessage());
        return new ResponseEntity<>(ResponseDTO.ofError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
