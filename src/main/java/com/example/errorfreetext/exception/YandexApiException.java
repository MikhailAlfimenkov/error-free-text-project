package com.example.errorfreetext.exception;

public class YandexApiException extends AppException {
    public YandexApiException(String message) {
        super("Yandex API error: " + message, 50201);
    }
}