package com.example.errorfreetext.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CheckTextRequest {
    private List<String> text;
    private String lang;
    private int options;
}
