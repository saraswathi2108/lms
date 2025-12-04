package com.example.payroll.Repository;

import com.example.payroll.entity.OfferLetter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferRepository extends JpaRepository<OfferLetter, Long> {


    Optional<OfferLetter> findByEmployeeId(String employeeId);
}
