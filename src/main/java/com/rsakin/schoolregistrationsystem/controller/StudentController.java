package com.rsakin.schoolregistrationsystem.controller;

import com.rsakin.schoolregistrationsystem.model.entity.Student;
import com.rsakin.schoolregistrationsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    // TODO : Mapper Usage with DTO

    @GetMapping(value = "/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> allStudents = studentService.getAllStudents();
        return new ResponseEntity<>(allStudents, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> getStudent(
            @PathVariable @Min(1) final Long id
    ) {
        Student student = studentService.getStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Student> saveStudent(
            @Valid @RequestBody final Student student
    ) {
        Student savedStudent = studentService.addStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable @Min(1) final Long id,
            @Valid @RequestBody final Student student
    ) {
        Student respStudent = studentService.updateStudent(id, student);
        return new ResponseEntity<>(respStudent, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> deleteStudent(
            @RequestParam @Min(1) final Long id
    ) {
        studentService.getStudentById(id);
        boolean isDeleted = studentService.deleteStudent(id);
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }

    @PutMapping("/:studentId/register/:courseId")
    public ResponseEntity<Student> registerCourse(
            @PathVariable @Min(1) final Long studentId,
            @PathVariable @Min(1) final Long courseId
    ) {
        Student student = studentService.registerCourse(studentId, courseId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    // Filter all students with a specific course
    @GetMapping("/course/:courseId")
    public ResponseEntity<List<Student>> getAllStudentsByCourse(
            @PathVariable @Min(1) final Long courseId
    ) {
        List<Student> allStudentsByCourse = studentService.getAllStudentsByCourse(courseId);
        return new ResponseEntity<>(allStudentsByCourse, HttpStatus.OK);
    }

    // Filter all students without any courses
    @GetMapping("/name/:name/surname/:surname")
    public ResponseEntity<List<Student>> getAllStudentsByNameAndSurname(
            @PathVariable @NotBlank @Size(max = 15) final String name,
            @PathVariable @NotBlank @Size(max = 20) final String surname
    ) {
        List<Student> allByNameAndSurname = studentService.findAllByNameAndSurname(name, surname);
        return new ResponseEntity<>(allByNameAndSurname, HttpStatus.OK);
    }

    @GetMapping("/surname/:surname")
    public ResponseEntity<List<Student>> getAllStudentsBySurname(
            @PathVariable @NotBlank @Size(max = 20) final String surname
    ) {
        List<Student> allBySurname = studentService.findAllBySurname(surname);
        return new ResponseEntity<>(allBySurname, HttpStatus.OK);
    }


}
