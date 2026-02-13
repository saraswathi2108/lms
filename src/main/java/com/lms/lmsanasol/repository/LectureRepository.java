package com.lms.lmsanasol.repository;


import com.lms.lmsanasol.entity.Course;
import com.lms.lmsanasol.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {


    List<Lecture> findByCourseIdOrderByOrderIndexAsc(Long courseId);


}
