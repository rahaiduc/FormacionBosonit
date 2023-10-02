package com.block7crudvalidation.controller.dto.outputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfesorOutputDto {
    private String id_profesor;
    private String id_persona;
    private String comments;
    private String branch;
}
