package com.lms.lmsanasol.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LectureResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Integer orderIndex;
    private Boolean isPreview;
    private String videoGuid;
    private String videoStatus;
    private String playbackUrl;
}
