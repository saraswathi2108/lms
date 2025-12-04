package com.example.payroll.Repository;


import com.example.payroll.entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends JpaRepository<Jobs , Long> {

    //Jobs findByJobtitle(String JobTitle);
}
