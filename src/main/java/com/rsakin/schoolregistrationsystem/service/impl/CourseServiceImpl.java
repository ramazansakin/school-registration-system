package com.rsakin.schoolregistrationsystem.service.impl;

import com.rsakin.schoolregistrationsystem.exception.EntityNotFoundException;
import com.rsakin.schoolregistrationsystem.model.entity.Course;
import com.rsakin.schoolregistrationsystem.model.entity.Student;
import com.rsakin.schoolregistrationsystem.repo.CourseRepository;
import com.rsakin.schoolregistrationsystem.service.CourseService;
import com.rsakin.schoolregistrationsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;
    private StudentService studentService;

    // Field Injection for student service to avoid circular dependency
    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             @Lazy StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    // A course has 50 students maximum
    private static final Integer MAX_ENROLLED_STUDENT_NUMBER = 50;

    // CRUD Operations
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(final Long id) {
        Optional<Course> byId = courseRepository.findById(id);
        return byId.orElseThrow(() -> new EntityNotFoundException("Course by id : " + id + " "));
    }

    @Override
    public Course addCourse(final Course airport) {
        return courseRepository.save(airport);
    }

    @Override
    public Course updateCourse(final Long id, final Course airport) {
        getCourseById(id);
        airport.setId(id);
        return courseRepository.save(airport);
    }

    @Override
    public boolean deleteCourse(final Long id) {
        courseRepository.delete(getCourseById(id));
        return true;
    }

    // Filter all courses for a specific student
    @Override
    public List<Course> getAllCoursesByStudent(final Long studentId) {
        // check the student is already defined on db before
        studentService.getStudentById(studentId);

        List<Course> allCourses = getAllCourses();
        return allCourses.stream().filter(course -> {
            List<Long> studentIds = course.getStudents().stream().map(Student::getId).collect(Collectors.toList());
            return studentIds.contains(studentId);
        }).collect(Collectors.toList());
    }

    // Filter all courses without any students
    @Override
    public List<Course> getAllCoursesByTitle(final String title) {
        return courseRepository.findAllByTitleContainingIgnoreCase(title);
    }

}
