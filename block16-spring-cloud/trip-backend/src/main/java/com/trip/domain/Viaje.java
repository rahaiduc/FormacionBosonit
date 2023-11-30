package com.trip.domain;

import com.trip.domain.dto.input.ViajeInputDto;
import com.trip.domain.dto.output.ViajeOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="viaje")
public class Viaje {
    @Id
    @GeneratedValue
    private int id_viaje;
    private String origen;
    private String destino;
    private String fechaSalida;
    private String fechaLlegada;
    private String estado;
    @ManyToMany(mappedBy = "viajes")
    private List<Cliente> pasajeros;

    public ViajeOutputDto viajeToViajeOutput(){
        List<Integer> list = this.pasajeros.stream().map(Cliente -> Cliente.getId_cliente()).toList();
        return new ViajeOutputDto(this.id_viaje,this.origen,this.destino,this.fechaSalida,this.fechaLlegada,this.estado,list);
    }
}
