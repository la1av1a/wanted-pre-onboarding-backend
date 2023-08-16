package com.example.wantedpreonboardingbackend.common.exception;

import lombok.Getter;

@Getter
public class MemberAlreadyExistsException extends RuntimeException{

    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}
