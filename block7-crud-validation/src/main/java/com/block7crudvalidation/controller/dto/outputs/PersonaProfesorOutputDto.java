package com.block7crudvalidation.controller.dto.outputs;

import com.block7crudvalidation.domain.Profesor;
import com.block7crudvalidation.domain.Student;
import com.block7crudvalidation.domain.branchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaProfesorOutputDto extends PersonOutputDto{
    private ProfesorOutputDto profesorOutputDto;
}
