package com.example.payroll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryCalculationRequest {
    private String employeeId;
    private Integer month;
    private Integer year;
}