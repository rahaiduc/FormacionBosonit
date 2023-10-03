package com.block7crudvalidation.repository;

import com.block7crudvalidation.domain.Persona;
import com.block7crudvalidation.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
