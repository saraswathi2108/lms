package com.example.payroll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequest {
    private String employeeId;
    private Integer month;
    private Integer year;
    private Integer totalWorkingDays;
    private Float daysPresent;
    private Float unpaidLeaves;
}