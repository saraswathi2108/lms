package com.example.payroll.handlers;

public class ApplicationResponse extends RuntimeException {
    public ApplicationResponse(String message) {
        super(message);
    }
}
