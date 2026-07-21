package com.example.errorfreetext.dto;

import com.example.errorfreetext.enums.Status;
import java.util.UUID;

public class TextChunkResponse {
    private UUID id;
    private String textValue;
    private String correctedValue;
    private String lang;
    private Status status;
    private Integer chunkOrder;

    public TextChunkResponse() {

    }

    public UUID getId() {
        return id;
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

    public Status getStatus() {
        return status;
    }

    public Integer getChunkOrder() {
        return chunkOrder;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setChunkOrder(Integer chunkOrder) {
        this.chunkOrder = chunkOrder;
    }
}

