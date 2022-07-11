package com.rsakin.schoolregistrationsystem.exception;

public class AlreadyEnrolledCourseException extends RuntimeException {
    public AlreadyEnrolledCourseException(String message) {
        super(message);
    }
}