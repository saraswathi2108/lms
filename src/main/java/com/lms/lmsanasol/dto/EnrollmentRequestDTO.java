package com.lms.lmsanasol.dto;

import lombok.Data;

@Data
public class EnrollmentRequestDTO {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String collegeName;
    private Integer yearOfStudy;
}
