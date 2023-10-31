package com.block7crudvalidation.repository;

import com.block7crudvalidation.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Persona, String>,PersonRepositoryCustom, PagingAndSortingRepository<Persona, String> {
    @Query("SELECT t FROM Persona t WHERE t.name=?1")
    List<Persona> findByName(String nombre);


}

