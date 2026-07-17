package com.example.errorfreetext.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "text_chunks")
public class TextChunk {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "text_value", nullable = false, columnDefinition = "TEXT")
    private String textValue;

    @Column(name = "corrected_value", columnDefinition = "TEXT")
    private String correctedValue;

    @Column(nullable = false, length = 10)
    private String lang;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(name = "chunk_order", nullable = false)
    private Integer chunkOrder;

    public TextChunk() {

    }

    public UUID getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public String getTextValue() {
        return textValue;
    }

    public String getCorrectedValue() {
        return correctedValue;
    }

    public String getLang() {
        return lang;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Integer getChunkOrder() {
        return chunkOrder;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public void setCorrectedValue(String correctedValue) {
        this.correctedValue = correctedValue;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setChunkOrder(Integer chunkOrder) {
        this.chunkOrder = chunkOrder;
    }
}
