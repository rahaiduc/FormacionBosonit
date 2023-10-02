package com.block7crudvalidation.domain.Mappers;

import com.block7crudvalidation.controller.dto.inputs.AsignaturaInputDto;
import com.block7crudvalidation.domain.Asignatura;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface AsignaturaMapper {
    AsignaturaMapper INSTANCE=Mappers.getMapper(AsignaturaMapper.class);
    Asignatura asignaturaInputDtotoAsignatura(AsignaturaInputDto asignaturaInputDto);
}
