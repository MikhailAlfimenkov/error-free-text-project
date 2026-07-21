package com.example.errorfreetext.service;

import com.example.errorfreetext.entity.Task;
import com.example.errorfreetext.entity.TextChunk;

import java.util.UUID;

public interface ChunkService {
    void processChunk(UUID id);
}
