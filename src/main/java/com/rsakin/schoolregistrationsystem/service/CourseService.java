package com.rsakin.schoolregistrationsystem.service;

import com.rsakin.schoolregistrationsystem.model.entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();

    Course getCourseById(final Long id);

    Course addCourse(final Course airport);

    Course updateCourse(final Long id, final Course airport);

    boolean deleteCourse(final Long id);

    // Filter all courses for a specific student
    List<Course> getAllCoursesByStudent(final Long studentId);

    // Filter all courses without any students
    List<Course> getAllCoursesByTitle(final String title);

}
