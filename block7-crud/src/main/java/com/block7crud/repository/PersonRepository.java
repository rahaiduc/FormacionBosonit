package com.block7crud.repository;

import com.block7crud.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Persona, Integer> {
    @Query("SELECT t FROM Persona t WHERE t.name=?1")
    List<Persona> findByName(String nombre);
}

