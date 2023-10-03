package com.block7crudvalidation.application.interfaces;

import com.block7crudvalidation.controller.dto.inputs.AsignaturaInputDto;
import com.block7crudvalidation.controller.dto.outputs.AsignaturaOutputDto;

import java.util.List;

public interface AsignaturaService {
    AsignaturaOutputDto addAsignatura(AsignaturaInputDto Asignatura);
    AsignaturaOutputDto getAsignaturaById(String id);
    void deleteAsignaturaById( String id);
    List<AsignaturaOutputDto> getAllAsignaturas();
    AsignaturaOutputDto updateAsignatura(AsignaturaInputDto Asignatura);
}
