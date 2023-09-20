package com.block6personcontrollers.servicios;

import com.block6personcontrollers.modelos.Persona;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Data
public class PersonService {
    Persona persona;
    public Persona devolverPersona(String nombre, String poblacion, int edad){
        return this.persona=new Persona(nombre,poblacion,edad);
    }
}
