package com.example.payroll.handlers;

public class ErrorException extends RuntimeException {
    public ErrorException(String message) {
        super(message);
    }
}
