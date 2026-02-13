package com.lms.lmsanasol.service;

import com.lms.lmsanasol.dto.CourseRequestDTO;
import com.lms.lmsanasol.dto.CourseResponseDTO;
import com.lms.lmsanasol.dto.CourseWithLecturesDTO;
import com.lms.lmsanasol.dto.LectureResponseDTO;
import com.lms.lmsanasol.entity.Course;
import com.lms.lmsanasol.entity.Lecture;
import com.lms.lmsanasol.repository.CourseRepository;
import com.lms.lmsanasol.repository.EnrollmentRepository;
import com.lms.lmsanasol.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;

    private  final EnrollmentRepository enrollmentRepository;
    public CourseResponseDTO createCourse(CourseRequestDTO dto) {

        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setSubtitle(dto.getSubtitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());

        Course saved = courseRepository.save(course);

        return mapToResponse(saved);
    }




    private CourseResponseDTO mapToResponse(Course course) {

        return CourseResponseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .subtitle(course.getSubtitle())
                .description(course.getDescription())
                .price(course.getPrice())
                .build();
    }

    @Transactional(readOnly = true)
    public CourseResponseDTO getCourseById(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        return mapToResponse(course);
    }


    @Transactional(readOnly = true)
    public CourseWithLecturesDTO getCourseWithVideos(
            Long courseId,
            Long userId,
            boolean isAdmin
    ) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        if (!isAdmin) {
            boolean enrolled = enrollmentRepository.existsByUserIdAndCourseId(userId, courseId);
            if (!enrolled) {
                throw new RuntimeException(
                        "Not enrolled in this course");
            }
        }
        List<LectureResponseDTO> lectureList =
                lectureRepository
                        .findByCourseIdOrderByOrderIndexAsc(courseId)
                        .stream()
                        .map(this::mapLecture)
                        .toList();
        return CourseWithLecturesDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .lectures(lectureList)
                .build();
    }

    private LectureResponseDTO mapLecture(Lecture lecture) {

        String playbackUrl = "https://iframe.mediadelivery.net/embed/"
                + lecture.getVideoLibraryId()
                + "/"
                + lecture.getVideoGuid();

        return LectureResponseDTO.builder()
                .id(lecture.getId())
                .title(lecture.getTitle())
                .description(lecture.getDescription())
                .orderIndex(lecture.getOrderIndex())
                .isPreview(lecture.getIsPreview())
                .videoStatus(lecture.getVideoStatus().name())
                .videoGuid(lecture.getVideoGuid())
                .playbackUrl(playbackUrl)
                .build();
    }

    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

}
