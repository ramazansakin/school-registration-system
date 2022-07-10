package com.rsakin.schoolregistrationsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

// Not used because of Data lombok annotation causes StackOverflow exception because of circular dependency
// @Data
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String details;

    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.ALL})
    @Setter
    private Set<Student> students;

    // JsonIgnore and overridden toString method to avoid StackOverflowError
    // by circular dependency
    @JsonIgnore
    public Set<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
