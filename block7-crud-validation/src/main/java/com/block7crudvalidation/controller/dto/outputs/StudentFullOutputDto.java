package com.block7crudvalidation.controller.dto.outputs;

import com.block7crudvalidation.domain.Asignatura;
import com.block7crudvalidation.domain.Persona;
import com.block7crudvalidation.domain.Profesor;
import com.block7crudvalidation.domain.branchType;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFullOutputDto {
    String id_student;
    int num_hours_week;
    String comments;
    branchType branch;
    PersonOutputDto personOutputDto;
    ProfesorOutputDto profesorOutputDto;
    Set<AsignaturaOutputDto> asignaturas;
}
