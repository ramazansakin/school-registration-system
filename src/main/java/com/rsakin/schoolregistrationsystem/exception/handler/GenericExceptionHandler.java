package com.rsakin.schoolregistrationsystem.exception.handler;

import com.rsakin.schoolregistrationsystem.exception.EntityNotFoundException;
import com.rsakin.schoolregistrationsystem.exception.MaxEnrolledCourseNumberExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(MaxEnrolledCourseNumberExceededException.class)
    public ResponseEntity<Map> handleMaxEnrolledCourseNumberExceededException(MaxEnrolledCourseNumberExceededException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("error_message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map> handleEntityNotFoundException(EntityNotFoundException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("error_message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map> handleGeneralException(Exception exception) {
        Map<String, String> response = new HashMap<>();
        response.put("error_message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

}