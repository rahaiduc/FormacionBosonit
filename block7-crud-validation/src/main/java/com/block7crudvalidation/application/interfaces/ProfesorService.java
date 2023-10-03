package com.block7crudvalidation.application.interfaces;

import com.block7crudvalidation.controller.dto.inputs.ProfesorInputDto;
import com.block7crudvalidation.controller.dto.outputs.ProfesorOutputDto;

import java.util.List;

public interface ProfesorService {
    ProfesorOutputDto addProfesor(ProfesorInputDto Profesor);
    ProfesorOutputDto getProfesorById(String id);
    void deleteProfesorById( String id);
    List<ProfesorOutputDto> getAllProfesors();
    ProfesorOutputDto updateProfesor(ProfesorInputDto Profesor);
}
