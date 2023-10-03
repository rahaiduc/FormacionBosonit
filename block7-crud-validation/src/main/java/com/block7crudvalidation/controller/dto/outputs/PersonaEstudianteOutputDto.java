package com.block7crudvalidation.controller.dto.outputs;

import com.block7crudvalidation.domain.Asignatura;
import com.block7crudvalidation.domain.Profesor;
import com.block7crudvalidation.domain.branchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaEstudianteOutputDto {
    private String id_student;
    private int num_hours_week;
    private String comments;
    private Profesor profesor;
    private branchType branch;
    private List<Asignatura> asignaturas;
}
