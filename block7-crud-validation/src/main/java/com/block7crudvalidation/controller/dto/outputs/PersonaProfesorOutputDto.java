package com.block7crudvalidation.controller.dto.outputs;

import com.block7crudvalidation.domain.Student;
import com.block7crudvalidation.domain.branchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaProfesorOutputDto {
    private String id_profesor;
    private String id_persona;
    private String comments;
    private branchType branch;

    private List<Student> estudiantes;
}
