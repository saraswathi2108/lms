package com.lms.lmsanasol.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CourseWithLecturesDTO {

    private Long id;
    private String title;
    private String description;
    private List<LectureResponseDTO> lectures;
}
