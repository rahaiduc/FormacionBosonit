package com.trip.domain;

import com.trip.domain.dto.input.ClienteInputDto;
import com.trip.domain.dto.output.ClienteOutputDto;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
public class Cliente {
    @Id
    @GeneratedValue
    private int id_cliente;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String email;
    private String telefono;
    @ManyToMany
    private List<Viaje> viajes;


    public ClienteOutputDto clienteToClienteOutput(){
        List<Integer> list = this.viajes.stream().map(Viaje -> Viaje.getId_viaje()).toList();
        return new ClienteOutputDto(this.id_cliente,this.nombre,this.apellido,this.edad,this.email,this.telefono,list);
    }
}
