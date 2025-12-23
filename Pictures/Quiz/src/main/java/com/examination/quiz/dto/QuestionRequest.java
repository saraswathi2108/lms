package com.examination.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {

    @NotBlank
    private String questionText;

    @NotBlank
    private String questionType;

    @NotNull
    private Integer marks;

    @Size(min = 2)
    private List<String> options;

    @NotEmpty
    private List<Integer> correctOptionIndexes;
}
