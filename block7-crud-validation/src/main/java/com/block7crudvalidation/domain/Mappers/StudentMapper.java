package com.block7crudvalidation.domain.Mappers;

import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.block7crudvalidation.domain.Student;
@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE= Mappers.getMapper(StudentMapper.class);

    Student studentInputDtoToStudent(StudentInputDto student);
}
