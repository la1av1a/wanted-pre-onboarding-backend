package com.example.wantedpreonboardingbackend.common.exception;

import lombok.Getter;

@Getter
public class UnAuthorizedException extends RuntimeException{

    public UnAuthorizedException(String message){
        super(message);
    }
}
