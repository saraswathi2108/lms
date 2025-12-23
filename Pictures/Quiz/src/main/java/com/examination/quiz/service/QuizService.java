package com.examination.quiz.service;

import com.examination.quiz.client.CourseClient;
import com.examination.quiz.dto.*;
import com.examination.quiz.entity.Option;
import com.examination.quiz.entity.Question;
import com.examination.quiz.entity.Quiz;
import com.examination.quiz.repository.OptionRepository;
import com.examination.quiz.repository.QuestionRepository;
import com.examination.quiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final CourseClient courseClient;

    // ===============================
    // CREATE SINGLE QUIZ
    // ===============================
    public QuizCreateResponse createQuiz(QuizCreateRequest request) {

        validateRequest(request);
        validateWithCourseService(request);

        Quiz quiz = Quiz.builder()
                .quizType(request.getQuizType())
                .courseId(request.getCourseId())
                .lectureId(request.getLectureId()) // null for GRAND
                .totalMarks(request.getTotalMarks())
                .build();

        quizRepository.save(quiz);

        List<Question> questions = new ArrayList<>();
        List<Option> options = new ArrayList<>();

        for (QuestionRequest qr : request.getQuestions()) {

            Question question = Question.builder()
                    .quiz(quiz)
                    .questionText(qr.getQuestionText())
                    .questionType(qr.getQuestionType())
                    .marks(qr.getMarks())
                    .build();

            questions.add(question);

            for (int i = 0; i < qr.getOptions().size(); i++) {
                options.add(
                        Option.builder()
                                .question(question)
                                .optionText(qr.getOptions().get(i))
                                .isCorrect(qr.getCorrectOptionIndexes().contains(i))
                                .build()
                );
            }
        }

        questionRepository.saveAll(questions);
        optionRepository.saveAll(options);

        return new QuizCreateResponse(
                quiz.getId(),
                quiz.getQuizType(),
                quiz.getCourseId(),
                quiz.getLectureId(),
                questions.size(),
                "Quiz created successfully"
        );
    }

    // ===============================
    // CREATE BULK QUIZZES
    // ===============================
    public List<QuizCreateResponse> createQuizzesBulk(
            List<QuizCreateRequest> requests
    ) {

        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("Quiz list cannot be empty");
        }

        List<QuizCreateResponse> responses = new ArrayList<>();

        for (QuizCreateRequest request : requests) {
            responses.add(createQuiz(request));
        }

        return responses;
    }

    // ===============================
    // REQUEST VALIDATION
    // ===============================
    private void validateRequest(QuizCreateRequest request) {

        if (request.getCourseId() == null) {
            throw new IllegalArgumentException("courseId is mandatory");
        }

        if (request.getQuizType() == null) {
            throw new IllegalArgumentException("quizType is mandatory");
        }

        // -------- LECTURE QUIZ --------
        if ("LECTURE".equals(request.getQuizType())) {

            if (request.getLectureId() == null) {
                throw new IllegalArgumentException(
                        "lectureId is mandatory for lecture quiz"
                );
            }

            if (quizRepository.existsByLectureId(request.getLectureId())) {
                throw new IllegalStateException(
                        "Quiz already exists for this lecture"
                );
            }
        }

        // -------- GRAND QUIZ --------
        if ("GRAND".equals(request.getQuizType())
                && request.getLectureId() != null) {
            throw new IllegalArgumentException(
                    "lectureId must be null for grand quiz"
            );
        }

        if (request.getQuestions() == null
                || request.getQuestions().isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one question is required"
            );
        }

        request.getQuestions().forEach(q -> {

            if ("MCQ".equals(q.getQuestionType())
                    && q.getCorrectOptionIndexes().size() != 1) {
                throw new IllegalArgumentException(
                        "MCQ must have exactly one correct answer"
                );
            }

            q.getCorrectOptionIndexes().forEach(idx -> {
                if (idx < 0 || idx >= q.getOptions().size()) {
                    throw new IllegalArgumentException(
                            "Invalid correct option index"
                    );
                }
            });
        });
    }

    // ===============================
    // COURSE / LECTURE VALIDATION
    // ===============================
    private void validateWithCourseService(QuizCreateRequest request) {

        // ✅ Course must exist (both GRAND & LECTURE)
        ApiResponse<CourseResponseDTO> courseResponse =
                courseClient.getCourse(request.getCourseId());

        if (courseResponse == null || courseResponse.getData() == null) {
            throw new IllegalArgumentException("Course not found");
        }

        // ✅ Lecture exists (LECTURE quiz only)
        if ("LECTURE".equals(request.getQuizType())) {

            ApiResponse<LectureResponseDTO> lectureResponse =
                    courseClient.getLecture(
                            request.getCourseId(),
                            request.getLectureId()
                    );

            if (lectureResponse == null
                    || lectureResponse.getData() == null) {
                throw new IllegalArgumentException("Lecture not found");
            }


        }
    }

    public List<QuizCreateResponse> getQuizzesByCourse(Long courseId) {

        List<Quiz> quizzes = quizRepository.findByCourseId(courseId);

        return quizzes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    public QuizCreateResponse getGrandQuizByCourse(Long courseId) {

        Quiz quiz = quizRepository
                .findByCourseIdAndQuizType(courseId, "GRAND")
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Grand quiz not found"));

        return mapToResponse(quiz);
    }

    public QuizCreateResponse getQuizByLecture(Long lectureId) {

        Quiz quiz = quizRepository
                .findByLectureId(lectureId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Lecture quiz not found"));

        return mapToResponse(quiz);
    }


    private QuizCreateResponse mapToResponse(Quiz quiz) {

        return new QuizCreateResponse(
                quiz.getId(),
                quiz.getQuizType(),
                quiz.getCourseId(),
                quiz.getLectureId(),
                quiz.getTotalMarks(),
                "Quiz fetched successfully"
        );
    }
}
