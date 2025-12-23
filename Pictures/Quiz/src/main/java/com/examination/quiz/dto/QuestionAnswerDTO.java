package com.examination.quiz.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionAnswerDTO {

    private Long questionId;

    // index-based answers (MCQ / MSQ)
    private List<Integer> selectedOptionIndexes;
}
