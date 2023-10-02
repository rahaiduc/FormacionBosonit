package com.block7crudvalidation.application.impl;

import com.block7crudvalidation.application.interfaces.ProfesorService;
import com.block7crudvalidation.controller.dto.inputs.ProfesorInputDto;
import com.block7crudvalidation.controller.dto.outputs.ProfesorOutputDto;
import org.springframework.stereotype.Service;

@Service
public class ProfesorServiceImpl implements ProfesorService {
    @Override
    public ProfesorOutputDto addProfesor(ProfesorInputDto Profesor) {
        return null;
    }

    @Override
    public ProfesorOutputDto getProfesorById(String id) {
        return null;
    }

    @Override
    public void deleteProfesorById(String id) {

    }

    @Override
    public Iterable<ProfesorOutputDto> getAllProfesors(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public ProfesorOutputDto updateProfesor(ProfesorInputDto Profesor) {
        return null;
    }
}
