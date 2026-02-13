package com.lms.lmsanasol.controller;


import com.lms.lmsanasol.dto.CourseRequestDTO;
import com.lms.lmsanasol.dto.CourseResponseDTO;
import com.lms.lmsanasol.dto.CourseWithLecturesDTO;
import com.lms.lmsanasol.entity.User;
import com.lms.lmsanasol.repository.UserRepository;
import com.lms.lmsanasol.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lms")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final UserRepository userRepository;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CourseResponseDTO createCourse(
            @Valid @RequestBody CourseRequestDTO request) {

        return courseService.createCourse(request);
    }




    @GetMapping("/courses")
    public List<CourseResponseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }



    @GetMapping("/{id}")
    public CourseResponseDTO getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }



    @GetMapping("/courses/{courseId}/content")
    public CourseWithLecturesDTO getCourseContent(
            @PathVariable Long courseId,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean isAdmin =
                user.getRole().name().equals("ADMIN");

        return courseService.getCourseWithVideos(
                courseId,
                user.getId(),
                isAdmin
        );
    }

}
