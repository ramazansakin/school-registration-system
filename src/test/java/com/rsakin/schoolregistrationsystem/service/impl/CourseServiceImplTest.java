package com.rsakin.schoolregistrationsystem.service.impl;

import com.rsakin.schoolregistrationsystem.model.entity.Course;
import com.rsakin.schoolregistrationsystem.model.entity.Student;
import com.rsakin.schoolregistrationsystem.repo.CourseRepository;
import com.rsakin.schoolregistrationsystem.service.StudentService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private CourseServiceImpl courseService;


    @Test
    void getAllCourses() {
        // init
        List<Course> sampleTestCourses = getSampleTestCourses();

        // stub - when
        when(courseRepository.findAll()).thenReturn(sampleTestCourses);

        // then - test
        List<Course> allActualCourses = courseService.getAllCourses();
        Assert.assertEquals(sampleTestCourses.size(), allActualCourses.size());

        for (Course expected : sampleTestCourses) {
            Optional<Course> actual = allActualCourses
                    .stream().filter(
                            airportCompany -> airportCompany.getId() == expected.getId()
                    ).findFirst();
            // Title is enough to test because of uniqueness
            Assert.assertEquals(expected.getTitle(), actual.get().getTitle());
            Assert.assertEquals(expected.getDetails(), actual.get().getDetails());
        }

    }

    @Test
    void getCourseById() {
        // init expected data
        Course expectedCourse = getSampleTestCourses().get(0);

        // stub - when
        when(courseRepository.findById(any())).thenReturn(Optional.of(expectedCourse));

        // then - test
        Course actualCourseById = courseService.getCourseById(1L);

        Assert.assertEquals(expectedCourse.getTitle(), actualCourseById.getTitle());

    }

    @Test
    void addCourse() {
        // init expected data
        Course newCourse = new Course(11L, "New Course", "Details", null);

        // stub - when
        when(courseRepository.save(newCourse)).thenReturn(newCourse);

        // then - test
        courseService.addCourse(newCourse);

        // verify its already run
        verify(courseRepository, times(1)).save(newCourse);
    }

    @Test
    void deleteCourse() {
        // given - precondition or setup
        long courseId = 1L;
        Course course = getSampleTestCourses().get(0);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        doNothing().when(courseRepository).delete(course);

        // when -  action or the behaviour that we are going test
        courseService.deleteCourse(courseId);

        // then - verify the output
        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    void getAllCoursesByStudent() {
        // init expected data
        // related test student
        Student student = new Student();
        student.setId(2L);
        // related courses for above student
        Course expectedCourse1 = getSampleTestCourses().get(0);
        Set<Course> expectedCourses = new HashSet<>();
        expectedCourses.add(expectedCourse1);


        // stub - when
        when(studentService.getStudentById(any())).thenReturn(student);
        when(courseRepository.findAll()).thenReturn(getSampleTestCourses());

        // then - test
        List<Course> allActualCoursesByStudent = courseService.getAllCoursesByStudent(student.getId());

        Assert.assertEquals(expectedCourses.size(), allActualCoursesByStudent.size());

    }

    @Test
    void getAllCoursesByTitle() {
        // init expected data
        String containsStrOnTitle = "Tit";
        List<Course> expectedFilteredCoursesByTitle = getSampleTestCourses().stream().filter(course -> course.getTitle()
                .contains(containsStrOnTitle)).collect(Collectors.toList());

        // stub - when
        when(courseRepository.findAllByTitleContainingIgnoreCase(containsStrOnTitle))
                .thenReturn(expectedFilteredCoursesByTitle);

        // then - test
        List<Course> allActualCoursesByTitle = courseService.getAllCoursesByTitle(containsStrOnTitle);
        Assert.assertEquals(expectedFilteredCoursesByTitle.size(), allActualCoursesByTitle.size());
    }

    private List<Course> getSampleTestCourses() {

        Set<Student> sampleStudentList1 = new HashSet<>();
        Set<Student> sampleStudentList2 = new HashSet<>();
        Student sampleStudent1 = new Student();
        sampleStudent1.setId(1L);
        sampleStudent1.setName("John");
        sampleStudent1.setSurname("Doe");
        // --
        Student sampleStudent2 = new Student();
        sampleStudent2.setId(2L);
        sampleStudent2.setName("Karen");
        sampleStudent2.setSurname("Doe");
        // --
        Student sampleStudent3 = new Student();
        sampleStudent3.setId(3L);
        sampleStudent3.setName("Rajev");
        sampleStudent3.setSurname("Sign");

        // Sample students for course 1
        sampleStudentList1.add(sampleStudent1);
        sampleStudentList1.add(sampleStudent2);

        // Sample students for course 2
        sampleStudentList1.add(sampleStudent2);
        sampleStudentList1.add(sampleStudent3);

        return Arrays.asList(
                new Course(1L, "TitleX", "DetailsX", sampleStudentList1),
                new Course(2L, "TitleY", "DetailsY", sampleStudentList2),
                new Course(3L, "TitleZ", "DetailsZ", null)
        );
    }

}