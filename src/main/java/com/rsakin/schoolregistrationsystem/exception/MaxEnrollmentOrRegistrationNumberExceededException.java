package com.rsakin.schoolregistrationsystem.exception;

import com.rsakin.schoolregistrationsystem.model.entity.Student;

public class MaxEnrollmentOrRegistrationNumberExceededException extends RuntimeException {

    public MaxEnrollmentOrRegistrationNumberExceededException(final String error_message) {
        super(error_message);
    }

}
