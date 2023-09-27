package com.block7crudvalidation.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonOutputDto {
    private int id_persona;
    private String usuario;
    private String name;
    private String surname;
    private String company_email;
    private boolean active;
}
