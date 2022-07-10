package com.rsakin.schoolregistrationsystem.controller;

import com.rsakin.schoolregistrationsystem.model.entity.Course;
import com.rsakin.schoolregistrationsystem.service.CourseService;
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
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    // TODO : Mapper Usage with DTO

    @GetMapping(value = "/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> allCourses = courseService.getAllCourses();
        return new ResponseEntity<>(allCourses, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getCourse(
            @PathVariable @Min(1) final Long id
    ) {
        Course course = courseService.getCourseById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Course> saveCourse(
            @Valid @RequestBody final Course course
    ) {
        Course savedCourse = courseService.addCourse(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable @Min(1) final Long id,
            @Valid @RequestBody final Course course
    ) {
        Course respCourse = courseService.updateCourse(id, course);
        return new ResponseEntity<>(respCourse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Boolean> deleteCourse(
            @RequestParam @Min(1) final Long id
    ) {
        courseService.getCourseById(id);
        boolean isDeleted = courseService.deleteCourse(id);
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }

    // Filter all courses for a specific student
    @GetMapping("/student/:studentId")
    public ResponseEntity<List<Course>> getAllCoursesByStudent(
            @PathVariable @Min(1) final Long studentId
    ) {
        List<Course> allCoursesByStudent = courseService.getAllCoursesByStudent(studentId);
        return new ResponseEntity<>(allCoursesByStudent, HttpStatus.OK);
    }

    // Filter all courses without any students
    @GetMapping("/title/:title")
    public ResponseEntity<List<Course>> getAllCoursesByTitle(
            @PathVariable @NotBlank @Size(max = 15) final String title
    ) {
        List<Course> allCoursesByTitle = courseService.getAllCoursesByTitle(title);
        return new ResponseEntity<>(allCoursesByTitle, HttpStatus.OK);
    }

}
