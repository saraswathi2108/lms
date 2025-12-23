package com.examination.quiz.repository;

import com.examination.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    boolean existsByLectureId(Long lectureId);

    Optional<Quiz> findByCourseIdAndQuizType(Long courseId, String quizType);
}
