package com.block7crud.domain;

import com.block7crud.controller.dto.PersonInputDto;
import com.block7crud.controller.dto.PersonOutputDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.springframework.data.annotation.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue
    int id;
    String name;
    String edad;
    String poblacion;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Persona(PersonInputDto personInputDto) {
        this.id = personInputDto.getId();
        this.name = personInputDto.getName();
        this.edad = personInputDto.getEdad();
        this.poblacion =personInputDto.getPoblacion();
    }

    public PersonOutputDto personToPersonOutputDto() {
        return new PersonOutputDto(
                this.id,
                this.name,
                this.edad,
                this.poblacion
        );
    }

}

