package com.example.payroll.dto;

import com.example.payroll.entity.Attendance;
import com.example.payroll.entity.Employee;
import com.example.payroll.entity.Payroll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFullDetailsResponse {
    private Employee employee;
    private List<Attendance> attendanceRecords;
    private List<Payroll> payrolls;
}