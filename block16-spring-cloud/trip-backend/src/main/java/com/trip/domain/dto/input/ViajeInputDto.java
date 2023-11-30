package com.trip.domain.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViajeInputDto {
    private String origen;
    private String destino;
    private String fechaSalida;
    private String fechaLlegada;
    private String estado;
    private List<Integer> pasajeros;
}
