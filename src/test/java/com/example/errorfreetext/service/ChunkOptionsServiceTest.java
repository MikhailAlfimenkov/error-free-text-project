package com.example.errorfreetext.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChunkOptionsServiceTest {

    private ChunkOptionsService chunkOptionsService;

    @BeforeEach
    void setUp() {
        chunkOptionsService = new ChunkOptionsService();
    }

    @Test
    void containsUrl_WhenUrlPresent_ReturnsTrue() {
        assertTrue(chunkOptionsService.containsUrl("Ссылка на сайт: https://example.com работает"));
        assertTrue(chunkOptionsService.containsUrl("Зайди ан http://test.org"));
    }

    @Test
    void containsUrl_WhenNoUrl_ReturnsFalse() {
        assertFalse(chunkOptionsService.containsUrl("Обычный текст без ссылок"));
    }

    @Test
    void containsDigits_WhenDigitsPresent_ReturnsTrue() {
        assertTrue(chunkOptionsService.containsDigits("Текст с числом 12345"));
        assertTrue(chunkOptionsService.containsDigits("Версия 2.0"));
    }

    @Test
    void containsDigits_WhenNoDigits_ReturnsFalse() {
        assertFalse(chunkOptionsService.containsDigits("Текст только из букв и знаков!"));
    }
}