package com.example.errorfreetext.dto;

import com.example.errorfreetext.entity.TaskStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TaskResponse {

    private UUID id;
    private TaskStatus status;
    private String errorMessage;
    private LocalDateTime createdAt;
    private List<TextChunkResponse> chunks;

    public TaskResponse() {}

    public UUID getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<TextChunkResponse> getChunks() {
        return chunks;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setChunks(List<TextChunkResponse> chunks) {
        this.chunks = chunks;
    }
}
