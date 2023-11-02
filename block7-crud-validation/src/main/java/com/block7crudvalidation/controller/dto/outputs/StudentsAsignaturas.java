package com.block7crudvalidation.controller.dto.outputs;

import com.block7crudvalidation.domain.BranchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentsAsignaturas {
    String id_student;
    Set<AsignaturaOutputDto> asignaturas;
}
