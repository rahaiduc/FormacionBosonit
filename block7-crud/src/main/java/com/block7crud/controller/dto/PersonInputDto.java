package com.block7crud.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInputDto {
    int id;
    @NotNull(message = "Nombre obligatorio")
    @NotBlank(message = "Nombre obligatorio")
    String name;
    @NotNull(message = "Edad obligatoria")
    @NotBlank(message = "Edad obligotoria")
    String edad;
    @NotNull(message = "Poblacion obligatoria")
    @NotBlank(message = "Poblacion obligatoria")
    String poblacion;
}

