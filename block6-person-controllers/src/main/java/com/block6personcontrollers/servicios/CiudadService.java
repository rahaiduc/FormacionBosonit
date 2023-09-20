package com.block6personcontrollers.servicios;

import com.block6personcontrollers.modelos.Ciudad;
import com.block6personcontrollers.modelos.Persona;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class CiudadService {
    @Autowired
    List<Ciudad> ciudades;
    /*public List<Ciudad> devolverCiudades(@Autowired List<Ciudad> ciudades){
        return this.ciudades=ciudades;
    }*/

    public void addCiudad(Ciudad c){
        ciudades.add(c);
    }
}
