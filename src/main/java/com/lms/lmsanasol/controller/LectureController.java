package com.lms.lmsanasol.controller;

import com.lms.lmsanasol.dto.LectureResponseDTO;
import com.lms.lmsanasol.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(
            value = "/api/lms/courses/{courseId}/lectures",
            consumes = "multipart/form-data"
    )
    public LectureResponseDTO createLecture(
            @PathVariable Long courseId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Integer orderIndex,
            @RequestParam(required = false) Boolean isPreview,
            @RequestPart MultipartFile videoFile
    ) throws Exception {

        return lectureService.createLectureWithVideo(
                courseId,
                title,
                description,
                orderIndex,
                isPreview,
                videoFile
        );
    }
}
