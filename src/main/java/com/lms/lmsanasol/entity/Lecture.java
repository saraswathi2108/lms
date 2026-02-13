package com.lms.lmsanasol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "lectures", indexes = {
        @Index(name = "idx_lecture_course", columnList = "course_id")
})
@Getter
@Setter
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private Integer orderIndex;

    private Integer durationSeconds;

    @Column(nullable = false)
    private Boolean isPreview = false;

    // Bunny fields
    private Long videoLibraryId;
    private String videoGuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VideoStatus videoStatus = VideoStatus.PROCESSING;

    private String thumbnailUrl;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
