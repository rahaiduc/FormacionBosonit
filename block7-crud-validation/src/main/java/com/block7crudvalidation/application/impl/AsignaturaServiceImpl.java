package com.block7crudvalidation.application.impl;

import com.block7crudvalidation.application.interfaces.AsignaturaService;
import com.block7crudvalidation.controller.dto.inputs.AsignaturaInputDto;
import com.block7crudvalidation.controller.dto.outputs.AsignaturaOutputDto;
import org.springframework.stereotype.Service;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {

    @Override
    public AsignaturaOutputDto addAsignatura(AsignaturaInputDto Asignatura) {
        return null;
    }

    @Override
    public AsignaturaOutputDto getAsignaturaById(String id) {
        return null;
    }

    @Override
    public void deleteAsignaturaById(String id) {

    }

    @Override
    public Iterable<AsignaturaOutputDto> getAllAsignaturas(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public AsignaturaOutputDto updateAsignatura(AsignaturaInputDto Asignatura) {
        return null;
    }
}
