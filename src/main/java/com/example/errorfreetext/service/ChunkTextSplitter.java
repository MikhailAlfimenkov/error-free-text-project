package com.example.errorfreetext.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChunkTextSplitter {

    private static final int MAX_CHUNK_SIZE = 10000;

    public List<String> splitTextIntoChunks(String text) {
        List<String> chunks = new ArrayList<>();

        if (text == null || text.isEmpty()) {
            return chunks;
        }

        if (text.length() <= MAX_CHUNK_SIZE) {
            chunks.add(text);
            return chunks;
        }

        String[] sentences = text.split("(?<=[.!?])\s*");
        StringBuilder currentChunk = new StringBuilder();

        for (String sentence : sentences) {
            int additionalSpace = !currentChunk.isEmpty() ? 1 : 0;

            if (currentChunk.length() + sentence.length() + additionalSpace <= MAX_CHUNK_SIZE) {
                if (!currentChunk.isEmpty()) {
                    currentChunk.append(" ");
                }
                currentChunk.append(sentence);
            } else {
                chunks.add(currentChunk.toString());
                currentChunk = new StringBuilder(sentence);
            }
        }

        if (!currentChunk.isEmpty()) {
            chunks.add(currentChunk.toString());
        }

        log.info("Text split into {} chunks", chunks.size());
        return chunks;
    }
}