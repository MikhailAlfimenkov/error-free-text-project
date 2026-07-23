package com.example.errorfreetext.exception;


import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {
    private final int errorCode;

    public AppException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
