package com.rsakin.schoolregistrationsystem.service.impl;

import com.rsakin.schoolregistrationsystem.exception.AlreadyEnrolledCourseException;
import com.rsakin.schoolregistrationsystem.exception.EntityNotFoundException;
import com.rsakin.schoolregistrationsystem.exception.MaxEnrollmentOrRegistrationNumberExceededException;
import com.rsakin.schoolregistrationsystem.model.entity.Course;
import com.rsakin.schoolregistrationsystem.model.entity.Student;
import com.rsakin.schoolregistrationsystem.repo.StudentRepository;
import com.rsakin.schoolregistrationsystem.service.CourseService;
import com.rsakin.schoolregistrationsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseService courseService;

    // A student can register to 5 course maximum
    private static final Integer MAX_REGISTERED_COURSE_NUMBER = 5;
    // A course has 50 students maximum
    private static final Integer MAX_ENROLLED_STUDENT_NUMBER = 50;

    // CRUD Operations
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(final Long id) {
        Optional<Student> byId = studentRepository.findById(id);
        return byId.orElseThrow(() -> new EntityNotFoundException("Student by id : " + id + " "));
    }

    @Override
    public Student addStudent(final Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(final Long id, final Student student) {
        getStudentById(id);
        student.setId(id);
        return studentRepository.save(student);
    }

    @Override
    public boolean deleteStudent(final Long id) {
        studentRepository.delete(getStudentById(id));
        return true;
    }

    @Override
    public Student registerCourse(final Long studentId, final Long courseId) {
        Student studentById = getStudentById(studentId);
        Course courseById = courseService.getCourseById(courseId);
        if (studentById.getCourses().size() >= MAX_REGISTERED_COURSE_NUMBER) {
            throw new MaxEnrollmentOrRegistrationNumberExceededException(
                    "Max course registration number is " + MAX_REGISTERED_COURSE_NUMBER
                            + " and it was exceeded by student by id : " + studentId
            );
        } else if (courseById.getStudents().size() >= MAX_ENROLLED_STUDENT_NUMBER) {
            throw new MaxEnrollmentOrRegistrationNumberExceededException(
                    "Max enrolled student number per course number is " + MAX_ENROLLED_STUDENT_NUMBER
                            + " and it was exceeded by course : " + courseId
            );
        }
        List<Long> studentRegisteredCourseIds =
                studentById.getCourses().parallelStream().map(Course::getId).collect(Collectors.toList());
        if (studentRegisteredCourseIds.contains(courseId)) {
            throw new AlreadyEnrolledCourseException("The student by id " + studentId +
                    " already registered this course [" + courseId + "]");
        }
        studentById.registerCourse(courseById);
        studentRepository.save(studentById);
        return studentById;
    }

    // Filter all students with a specific course
    @Override
    public List<Student> getAllStudentsByCourse(final Long courseId) {
        // check the course is already defined on db before
        courseService.getCourseById(courseId);

        List<Student> allStudents = getAllStudents();
        return allStudents.stream().filter(student -> {
            List<Long> studentCourseIds = student.getCourses().stream().map(Course::getId).collect(Collectors.toList());
            return studentCourseIds.contains(courseId);
        }).collect(Collectors.toList());
    }

    // Filter all students without any courses
    @Override
    public List<Student> findAllByNameAndSurname(final String name, final String surname) {
        return studentRepository
                .findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseOrderBySurname(name, surname);
    }

    @Override
    public List<Student> findAllBySurname(final String surname) {
        return studentRepository.findAllBySurnameContainingIgnoreCaseOrderBySurname(surname);
    }

}
