package com.block7crud.repository;

import com.block7crud.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Persona, Integer> {
}

