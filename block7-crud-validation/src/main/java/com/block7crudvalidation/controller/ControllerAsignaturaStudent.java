package com.block7crudvalidation.controller;

import com.block7crudvalidation.application.impl.StudentServiceImpl;
import com.block7crudvalidation.controller.dto.outputs.AsignaturaOutputDto;
import com.block7crudvalidation.domain.Asignatura;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("estudiante_asignatura")
public class ControllerAsignaturaStudent {
    @Autowired
    StudentServiceImpl studentService;

    @GetMapping("/{id}")
    public List<AsignaturaOutputDto> getAsignaturasEstudiante(@PathVariable String id){
        return studentService.getAsignaturasStudent(id);
    }
}
