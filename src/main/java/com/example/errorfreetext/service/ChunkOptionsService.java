package com.example.errorfreetext.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Slf4j
@Service
public class ChunkOptionsService {

    private static final Pattern URL_PATTERN = Pattern.compile(
            "https?://[\\w.~:/?#\\[\\]@!$&'()*+,;=-]+|www\\.[\\w.~:/?#\\[\\]@!$&'()*+,;=-]+",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");

    public boolean containsUrl(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return URL_PATTERN.matcher(text).find();
    }

    public boolean containsDigits(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return DIGIT_PATTERN.matcher(text).find();
    }
}