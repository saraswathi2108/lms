package com.examination.quiz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CourseResponseDTO {

    private Long courseId;
    private List<SectionDTO> sections;

    @Data
    public static class SectionDTO {
        private List<LectureDTO> lectures;
    }

    @Data
    public static class LectureDTO {
        private Long id;
    }
}
