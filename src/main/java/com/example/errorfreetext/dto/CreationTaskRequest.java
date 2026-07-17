package com.example.errorfreetext.dto;

public class CreationTaskRequest {
    private String text;
    private String lang;

    public CreationTaskRequest(){

    }

    public String getText() {
        return text;
    }

    public String getLang() {
        return lang;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
