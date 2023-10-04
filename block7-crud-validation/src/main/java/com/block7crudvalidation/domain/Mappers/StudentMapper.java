package com.block7crudvalidation.domain.Mappers;

import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.domain.Persona;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import com.block7crudvalidation.domain.Student;
@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentMapper INSTANCE= Mappers.getMapper(StudentMapper.class);

    Student studentInputDtoToStudent(StudentInputDto student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromDto(StudentInputDto dto, @MappingTarget Student entity);
}
