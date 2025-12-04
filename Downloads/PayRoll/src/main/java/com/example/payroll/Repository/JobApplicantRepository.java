package com.example.payroll.Repository;

import com.example.payroll.entity.JobApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicantRepository extends JpaRepository<JobApplicant, Long> {

}

