package com.examination.quiz.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizSubmitRequest {

    private Long quizId;

    private List<QuestionAnswerDTO> answers;
}
