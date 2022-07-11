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
        return byId.orElseThrow(() -> {
            log.error("Student entity not found with id : " + id);
            return new EntityNotFoundException("Student by id : " + id);
        });
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
            String formattedError = String.format("Max course registration number is %d" +
                    " and it was exceeded by student by id : %d", MAX_REGISTERED_COURSE_NUMBER, studentId);
            log.error(formattedError);
            throw new MaxEnrollmentOrRegistrationNumberExceededException(formattedError);
        } else if (courseById.getStudents().size() >= MAX_ENROLLED_STUDENT_NUMBER) {
            String formattedError = String.format("Max enrolled student number per course number is %d " +
                    "and it was exceeded by course : %d", MAX_ENROLLED_STUDENT_NUMBER, courseId);
            log.error(formattedError);
            throw new MaxEnrollmentOrRegistrationNumberExceededException(formattedError);
        }
        List<Long> studentRegisteredCourseIds =
                studentById.getCourses().parallelStream().map(Course::getId).collect(Collectors.toList());
        if (studentRegisteredCourseIds.contains(courseId)) {
            String formattedError = String.format("The student by id %d already registered this course [%d]",
                    studentId, courseId);
            log.error(formattedError);
            throw new AlreadyEnrolledCourseException(formattedError);
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
            // Get all courses Ids related to that student & compare with input
            List<Long> studentCourseIds = student.getCourses().stream().map(Course::getId).collect(Collectors.toList());
            return studentCourseIds.contains(courseId);
        }).collect(Collectors.toList());
    }

    // Filter all students without any courses
    @Override
    public List<Student> findAllByNameAndSurname(final String name, final String surname) {
        log.info("Get all Students by Name and Surname IgnoreCase and ordered by Surname");
        return studentRepository
                .findAllByNameContainingIgnoreCaseAndSurnameContainingIgnoreCaseOrderBySurname(name, surname);
    }

    @Override
    public List<Student> findAllBySurname(final String surname) {
        log.info("Get all Students by Surname IgnoreCase and ordered by Surname");
        return studentRepository.findAllBySurnameContainingIgnoreCaseOrderBySurname(surname);
    }

}
