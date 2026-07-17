package com.example.errorfreetext.entity;

import jakarta.persistence.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;


    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("chunkOrder ASC")
    private List<TextChunk> chunks = new ArrayList<>();


    public Task() {
    }

    public void addChunk(TextChunk chunk) {
        chunks.add(chunk);
        chunk.setTask(this);
    }

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

    public List<TextChunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<TextChunk> chunks) {
        this.chunks = chunks;
    }
}
