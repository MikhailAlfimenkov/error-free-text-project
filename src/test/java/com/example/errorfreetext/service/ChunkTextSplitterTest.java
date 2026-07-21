package com.example.errorfreetext.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChunkTextSplitterTest {

    private ChunkTextSplitter splitter;

    @BeforeEach
    void setUp() {
        splitter = new ChunkTextSplitter();
    }

    @Test
    void splitTextIntoChunks_ShortText_ReturnsSingleChunk() {
        String text = "Короткий текст.";
        List<String> chunks = splitter.splitTextIntoChunks(text);
        assertEquals(1, chunks.size());
        assertEquals("Короткий текст.", chunks.get(0));
    }

    @Test
    void splitTextIntoChunks_NullOrEmpty_ReturnsEmptyList() {
        assertTrue(splitter.splitTextIntoChunks(null).isEmpty());
        assertTrue(splitter.splitTextIntoChunks(" ").isEmpty());
    }
}