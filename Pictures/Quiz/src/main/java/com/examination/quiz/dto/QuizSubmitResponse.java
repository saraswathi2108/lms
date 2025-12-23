package com.examination.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizSubmitResponse {

    private Long quizId;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private Integer totalMarks;
    private Integer obtainedMarks;
    private Boolean passed;
}
