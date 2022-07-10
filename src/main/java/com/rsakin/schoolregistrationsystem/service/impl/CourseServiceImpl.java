package com.rsakin.schoolregistrationsystem.service.impl;

import com.rsakin.schoolregistrationsystem.exception.EntityNotFoundException;
import com.rsakin.schoolregistrationsystem.model.entity.Course;
import com.rsakin.schoolregistrationsystem.model.entity.Student;
import com.rsakin.schoolregistrationsystem.repo.CourseRepository;
import com.rsakin.schoolregistrationsystem.service.CourseService;
import com.rsakin.schoolregistrationsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    // Setter Injection for student service to avoid circular dependency
    private StudentService studentService;

    public void setStudentService(StudentService studentService) {
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
        return byId.orElseThrow(() -> new EntityNotFoundException("Airport by id : " + id + " "));
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
        studentService.getStudentById(studentId);

        List<Course> allCourses = getAllCourses();
        return allCourses.stream().filter(course -> {
            List<Long> courseIds = course.getStudents().stream().map(Student::getId).collect(Collectors.toList());
            return courseIds.contains(studentId);
        }).collect(Collectors.toList());
    }

    // Filter all courses without any students
    @Override
    public List<Course> getAllCoursesByTitle(final String title) {
        return courseRepository.findAllByTitle(title);
    }

}
