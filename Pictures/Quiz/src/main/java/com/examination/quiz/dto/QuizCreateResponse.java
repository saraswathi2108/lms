package com.examination.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizCreateResponse {

    private Long quizId;
    private String quizType;
    private Long courseId;
    private Long lectureId;
    private Integer totalQuestions;
    private String message;
}
