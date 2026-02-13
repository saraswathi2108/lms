package com.lms.lmsanasol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseRequestDTO {

    @NotBlank
    private String title;

    private String description;

    private String subtitle;

    @NotNull
    private BigDecimal price;
}
