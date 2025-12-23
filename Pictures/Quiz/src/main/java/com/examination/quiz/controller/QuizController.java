package com.examination.quiz.controller;

import com.examination.quiz.dto.*;
import com.examination.quiz.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<QuizCreateResponse> createQuiz(
            @Valid @RequestBody QuizCreateRequest request
    ) {
        QuizCreateResponse response = quizService.createQuiz(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<QuizCreateResponse>> createQuizzesBulk(
            @Valid @RequestBody List<QuizCreateRequest> quizzes
    ) {
        List<QuizCreateResponse> responses =
                quizService.createQuizzesBulk(quizzes);

        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<QuizCreateResponse>> getQuizzesByCourse(
            @PathVariable Long courseId
    ) {
        return ResponseEntity.ok(
                quizService.getQuizzesByCourse(courseId)
        );
    }
    @GetMapping("/lecture/{lectureId}")
    public ResponseEntity<QuizCreateResponse> getQuizByLecture(
            @PathVariable Long lectureId
    ) {
        return ResponseEntity.ok(
                quizService.getQuizByLecture(lectureId)
        );
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizSubmitResponse> submitQuiz(
            @RequestBody QuizSubmitRequest request
    ) {
        return ResponseEntity.ok(
                quizService.submitQuiz(request)
        );
    }




}
