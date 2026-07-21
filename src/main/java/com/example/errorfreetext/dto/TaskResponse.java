package com.example.errorfreetext.dto;

import com.example.errorfreetext.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class TaskResponse {

    private UUID id;
    private Status status;
    private String text;
    private String errorMessage;
}
