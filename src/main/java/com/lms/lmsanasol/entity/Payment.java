package com.lms.lmsanasol.entity;

import jakarta.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;

    @OneToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;


    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
