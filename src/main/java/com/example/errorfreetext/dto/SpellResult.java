package com.example.errorfreetext.dto;

import java.util.List;

public class SpellResult {

    private int code;
    private int pos;
    private int row;
    private int col;
    private int len;
    private String word;
    private List<String> s;

    public SpellResult(){}

    public int getCode() {
        return code;
    }

    public int getPos() {
        return pos;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getLen() {
        return len;
    }

    public String getWord() {
        return word;
    }

    public List<String> getS() {
        return s;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setS(List<String> s) {
        this.s = s;
    }
}
