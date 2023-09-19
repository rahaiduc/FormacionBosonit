package com.block6simplecontrollers;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Persona {
    private final String nombre;
    private final String poblacion;
    private final int edad;
}
