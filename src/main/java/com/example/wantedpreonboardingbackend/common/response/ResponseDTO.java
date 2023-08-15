package com.example.wantedpreonboardingbackend.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public record ResponseDTO<T>(@JsonInclude(Include.NON_NULL) T data, String message) {

    public static <T> ResponseDTO<T> ofError(String message) {
        return new ResponseDTO<>(null, message);
    }

    public static <T> ResponseDTO<T> ofSuccess(String message) {
        return new ResponseDTO<>(null, message);
    }
}
