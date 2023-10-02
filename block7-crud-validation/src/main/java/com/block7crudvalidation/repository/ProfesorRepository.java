package com.block7crudvalidation.repository;

import com.block7crudvalidation.domain.Persona;
import com.block7crudvalidation.domain.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {
}
