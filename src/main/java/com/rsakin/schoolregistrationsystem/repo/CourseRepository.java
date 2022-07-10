package com.rsakin.schoolregistrationsystem.repo;

import com.rsakin.schoolregistrationsystem.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByTitleContainingIgnoreCase(final String title);

}
