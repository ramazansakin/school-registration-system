package com.rsakin.schoolregistrationsystem.exception;

import com.rsakin.schoolregistrationsystem.model.entity.Student;

public class MaxEnrolledCourseNumberExceededException extends RuntimeException {

    public MaxEnrolledCourseNumberExceededException(final Student student, final Integer maxEnrollment) {
        super("Max course enrollment number is " + maxEnrollment + " and it was exceeded by student : " + student);
    }

}
