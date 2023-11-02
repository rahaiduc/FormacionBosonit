package com.block7crudvalidation.controller.dto.outputs;

import com.block7crudvalidation.domain.Asignatura;
import com.block7crudvalidation.domain.Profesor;
import com.block7crudvalidation.domain.BranchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaEstudianteOutputDto extends PersonOutputDto{
    private StudentFullOutputDto studentFullOutputDto;
}
