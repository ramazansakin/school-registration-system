package com.rsakin.schoolregistrationsystem.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rsakin.schoolregistrationsystem.exception.handler.GenericExceptionHandler;
import com.rsakin.schoolregistrationsystem.model.entity.Course;
import com.rsakin.schoolregistrationsystem.service.impl.CourseServiceImpl;
import com.rsakin.schoolregistrationsystem.service.impl.CourseServiceImplTest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    private MockMvc mvc;

    @Mock
    private CourseServiceImpl courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(courseController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void getAllCourses() throws Exception {
        // init test values / given
        List<Course> sampleExpectedTestCourses = CourseServiceImplTest.getSampleTestCourses();

        // stub - when
        Mockito.when(courseService.getAllCourses()).thenReturn(sampleExpectedTestCourses);

        MockHttpServletResponse response = mvc.perform(get("/api/course/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        // converting response to List of courses and compare with expected
        List<Course> actualCourses = new ObjectMapper().readValue(response.getContentAsString(),
                new TypeReference<>() {
                });
        assertEquals(sampleExpectedTestCourses.size(), actualCourses.size());

    }

    @Test
    void getCourse() {
        // Almost the same with getAll :)
    }

    @Test
    void saveCourse() throws Exception {
        // init test values
        Course newExpectedCourse = new Course();
        newExpectedCourse.setId(4L);
        newExpectedCourse.setTitle("NewCourseTitle");
        newExpectedCourse.setDetails("Sample details");

        // stub - given
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedCourseToJsonStr = ow.writeValueAsString(newExpectedCourse);

        MockHttpServletResponse response = mvc.perform(post("/api/course/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedCourseToJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        Mockito.verify(courseService, Mockito.times(1)).addCourse(any());
    }

    @Test
    void updateCourse() {
        // Almost the same with create :)
    }

    @Test
    void deleteCourse() throws Exception {
        // stub - given
        Mockito.when(courseService.deleteCourse(any())).thenReturn(true);

        MockHttpServletResponse response = mvc.perform(delete("/api/course/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String actualResponseStr = response.getContentAsString();
        Assert.assertEquals("true", actualResponseStr);
    }

    @Test
    void getAllCoursesByStudent() {
        // Almost the same with getAll :)
    }

    @Test
    void getAllCoursesByTitle() {
        // Almost the same with getAll :)
    }
}