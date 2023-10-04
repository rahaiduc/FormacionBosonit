package com.block7crudvalidation.controller.dto.outputs;

import com.block7crudvalidation.domain.Profesor;
import com.block7crudvalidation.domain.branchType;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSimpleOutputDto {
    String id_student;
    int num_hours_week;
    String comments;
    branchType branch;
}