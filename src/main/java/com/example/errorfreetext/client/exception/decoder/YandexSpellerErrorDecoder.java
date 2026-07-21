package com.example.errorfreetext.client.exception.decoder;


import com.example.errorfreetext.client.exception.YandexApiException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class YandexSpellerErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Yandex Speller API error. Status: {}, Method: {}, URL: {}",
                response.status(), methodKey, response.request().url());

        String responseBody = "";
        try {
            if (response.body() != null) {
                responseBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
                log.error("Response body: {}", responseBody);
            }
        } catch (Exception e) {
            log.error("Error reading response body", e);
        }


        return switch (response.status()) {
            case 400 -> new YandexApiException("Bad request to Yandex Speller API: " + responseBody);
            case 401, 403 -> new YandexApiException("Authentication error with Yandex Speller API");
            case 500, 502, 503, 504 -> new YandexApiException("Yandex Speller API server error: " + responseBody);
            default -> new YandexApiException("Yandex Speller API error (HTTP " + response.status() + "): " + responseBody);
        };
    }
}
