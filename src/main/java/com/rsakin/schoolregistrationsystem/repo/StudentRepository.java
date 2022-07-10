package com.rsakin.schoolregistrationsystem.repo;

import com.rsakin.schoolregistrationsystem.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllBySurnameOrderBySurname(final String surname);

    List<Student> findAllByNameAndSurnameOrderBySurname(final String name, final String surname);

}
