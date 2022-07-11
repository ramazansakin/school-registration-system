package com.rsakin.schoolregistrationsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

// Not used because of Data lombok annotation causes StackOverflow exception because of circular dependency
// @Data
@NoArgsConstructor
@AllArgsConstructor
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
    @NotEmpty
    private String title;

    @Getter
    @Setter
    @NotEmpty
    private String details;

    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.MERGE})
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
