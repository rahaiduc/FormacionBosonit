package com.block7crudvalidation.application.interfaces;

import com.block7crudvalidation.controller.dto.inputs.ProfesorInputDto;
import com.block7crudvalidation.controller.dto.outputs.ProfesorOutputDto;

public interface ProfesorService {
    ProfesorOutputDto addProfesor(ProfesorInputDto Profesor);
    ProfesorOutputDto getProfesorById(int id);
    void deleteProfesorById( int id);
    Iterable<ProfesorOutputDto> getAllProfesors(int pageNumber, int pageSize);
    ProfesorOutputDto updateProfesor(ProfesorInputDto Profesor);
}
