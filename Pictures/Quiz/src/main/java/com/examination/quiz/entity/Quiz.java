package com.examination.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "quizzes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"lecture_id"})
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String quizType; // LECTURE / GRAND

    @Column(nullable = false)
    private Long courseId;

    @Column(name = "lecture_id")
    private Long lectureId; // NULL for GRAND

    @Column(nullable = false)
    private Integer totalMarks;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
