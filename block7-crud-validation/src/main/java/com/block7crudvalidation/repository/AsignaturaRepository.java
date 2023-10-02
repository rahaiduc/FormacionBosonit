package com.block7crudvalidation.repository;

import com.block7crudvalidation.domain.Asignatura;
import com.block7crudvalidation.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Integer> {
}
