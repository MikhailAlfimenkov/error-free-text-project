package com.example.errorfreetext.client.exception;

import java.util.UUID;

public class ChunkNotFoundException extends AppException {
    public ChunkNotFoundException(UUID id) {
        super("Chunk with id: " + id + " not found", 40402);
    }
}
