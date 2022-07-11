package com.rsakin.schoolregistrationsystem.exception.handler;

import com.rsakin.schoolregistrationsystem.exception.AlreadyEnrolledCourseException;
import com.rsakin.schoolregistrationsystem.exception.EntityNotFoundException;
import com.rsakin.schoolregistrationsystem.exception.MaxEnrollmentOrRegistrationNumberExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GenericExceptionHandler {

    private static final String ERROR_MESSAGE = "error_message";

    @ExceptionHandler(AlreadyEnrolledCourseException.class)
    public ResponseEntity<Map> handleAlreadyEnrolledCourseException(
            MaxEnrollmentOrRegistrationNumberExceededException exception
    ) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_MESSAGE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MaxEnrollmentOrRegistrationNumberExceededException.class)
    public ResponseEntity<Map> handleMaxEnrolledCourseNumberExceededException(
            MaxEnrollmentOrRegistrationNumberExceededException exception
    ) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_MESSAGE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map> handleEntityNotFoundException(EntityNotFoundException exception) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_MESSAGE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map> handleGeneralException(Exception exception) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_MESSAGE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

}