package com.block7crudvalidation.controller.dto.outputs;

import com.block7crudvalidation.domain.branchType;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentOutputDto {
    String id_student;
    int num_hours_week;
    String comments;
    branchType branch;
    String id_persona;
    String usuario;
    String password;
    String name;
    String surname;
    String company_email;
    String personal_email;
    String city;
    boolean activate;
    Date created_date;
    String imagen_url;
    Date termination_date;


}
