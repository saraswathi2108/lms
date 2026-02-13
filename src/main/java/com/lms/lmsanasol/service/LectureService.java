package com.lms.lmsanasol.service;

import com.lms.lmsanasol.dto.LectureResponseDTO;
import com.lms.lmsanasol.entity.*;
import com.lms.lmsanasol.repository.CourseRepository;
import com.lms.lmsanasol.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureService {

    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;
    private final BunnyService bunnyService;

    public LectureResponseDTO createLectureWithVideo(
            Long courseId,
            String title,
            String description,
            Integer orderIndex,
            Boolean isPreview,
            MultipartFile videoFile
    ) throws Exception {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        String guid = bunnyService.createVideo(title);

        bunnyService.uploadVideoFile(guid, videoFile);

        Lecture lecture = new Lecture();
        lecture.setTitle(title);
        lecture.setDescription(description);
        lecture.setOrderIndex(orderIndex);
        lecture.setIsPreview(isPreview != null ? isPreview : false);
        lecture.setVideoGuid(guid);
        lecture.setVideoLibraryId(bunnyService.getLibraryId());
        lecture.setVideoStatus(VideoStatus.READY);
        lecture.setCourse(course);

        Lecture saved = lectureRepository.save(lecture);

        return mapToResponse(saved);
    }

    private LectureResponseDTO mapToResponse(Lecture lecture) {
        return LectureResponseDTO.builder()
                .id(lecture.getId())
                .title(lecture.getTitle())
                .description(lecture.getDescription())
                .orderIndex(lecture.getOrderIndex())
                .isPreview(lecture.getIsPreview())
                .videoGuid(lecture.getVideoGuid())
                .videoStatus(lecture.getVideoStatus().name())
                .build();
    }
}
