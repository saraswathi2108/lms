package com.examination.quiz.client;

import com.examination.quiz.dto.ApiResponse;
import com.examination.quiz.dto.CourseResponseDTO;
import com.examination.quiz.dto.LectureResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service", url = "http://192.168.0.249:8088")
public interface CourseClient {

    @GetMapping("/api/courses/{courseId}")
    ApiResponse<CourseResponseDTO> getCourse(@PathVariable Long courseId);

    @GetMapping("/api/courses/lectures/{lectureId}")
    ApiResponse<LectureResponseDTO> getLecture(@PathVariable Long lectureId);

//    @GetMapping("/api/courses/{courseId}/lectures/{lectureId}")
//    ApiResponse<LectureResponseDTO> getLecture(
//            @PathVariable Long courseId,
//            @PathVariable Long lectureId
//    );
@GetMapping("/api/courses/{courseId}/lectures/{lectureId}")
ApiResponse<LectureResponseDTO> getLecture(
        @PathVariable Long courseId,
        @PathVariable Long lectureId
);

}

// 8088 192.168.0.249
