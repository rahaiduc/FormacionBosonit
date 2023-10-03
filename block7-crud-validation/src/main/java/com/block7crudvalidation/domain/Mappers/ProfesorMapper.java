package com.block7crudvalidation.domain.Mappers;

import com.block7crudvalidation.controller.dto.inputs.ProfesorInputDto;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.domain.Persona;
import com.block7crudvalidation.domain.Profesor;
import com.block7crudvalidation.domain.Student;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfesorMapper {
    ProfesorMapper INSTANCE= Mappers.getMapper(ProfesorMapper.class);
    Profesor profesorInputDtoToProfesor(ProfesorInputDto profesorInputDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfesorFromDto(ProfesorInputDto dto, @MappingTarget Profesor entity);
}
