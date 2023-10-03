package com.block7crudvalidation.domain.Mappers;

import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonaEstudianteOutputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonaProfesorOutputDto;
import com.block7crudvalidation.domain.Persona;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE= Mappers.getMapper(PersonMapper.class);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePersonFromDto(PersonInputDto dto, @MappingTarget Persona entity);

    PersonaEstudianteOutputDto personaToEstudianteDto(Persona p);

    PersonaProfesorOutputDto personaToProfesor(Persona p);
}
