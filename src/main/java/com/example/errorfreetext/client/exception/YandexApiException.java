package com.example.errorfreetext.client.exception;

public class YandexApiException extends AppException {
    public YandexApiException(String message) {
        super("Yandex API error: " + message, 50201);
    }
}