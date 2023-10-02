package com.block7crudvalidation.application.interfaces;

import com.block7crudvalidation.controller.dto.inputs.AsignaturaInputDto;
import com.block7crudvalidation.controller.dto.outputs.AsignaturaOutputDto;

public interface AsignaturaService {
    AsignaturaOutputDto addAsignatura(AsignaturaInputDto Asignatura);
    AsignaturaOutputDto getAsignaturaById(int id);
    void deleteAsignaturaById( int id);
    Iterable<AsignaturaOutputDto> getAllAsignaturas(int pageNumber, int pageSize);
    AsignaturaOutputDto updateAsignatura(AsignaturaInputDto Asignatura);
}
