package com.lms.lmsanasol.repository;

import com.lms.lmsanasol.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

}
