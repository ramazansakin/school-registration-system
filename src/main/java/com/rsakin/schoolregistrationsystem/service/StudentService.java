package com.rsakin.schoolregistrationsystem.service;

import com.rsakin.schoolregistrationsystem.model.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentById(final Long id);

    Student addStudent(final Student airport);

    Student updateStudent(final Long id, final Student airport);

    boolean deleteStudent(final Long id);

    Student registerCourse(final Long studentId, final Long courseId);

    // Filter all students with a specific course
    List<Student> getAllStudentsByCourse(final Long courseId);

    // Filter all students without any courses
    List<Student> findAllByNameAndSurname(final String name, final String surname);

    List<Student> findAllBySurname(final String surname);

}
