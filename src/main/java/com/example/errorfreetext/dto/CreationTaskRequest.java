package com.example.errorfreetext.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreationTaskRequest {

    @NotBlank(message = "Text must not be blank")
    @Size(min = 3, message = "Text must contain at least 3 characters.")
    @Pattern(
            regexp = ".*[a-zA-Zа-яА-ЯёЁ].*",
            message = "Text must contain at least one letter."
    )
    private String text;

    @NotBlank(message = "Language must not be blank.")
    @Pattern(
            regexp = "^(?i)(RU|EN)$",
            message = "Language must be either RU or EN."
    )
    private String lang;



}
