package com.examination.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class QuizCreateRequest {

    @NotBlank
    private String quizType;

    @NotNull
    private Long courseId;

    private Long lectureId;

    @NotNull
    private Integer totalMarks;

    @NotEmpty
    private List<QuestionRequest> questions;
}
