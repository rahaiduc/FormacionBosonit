package com.block6personcontrollers.controladores;

import com.block6personcontrollers.modelos.Ciudad;
import com.block6personcontrollers.modelos.Persona;
import com.block6personcontrollers.servicios.CiudadService;
import com.block6personcontrollers.servicios.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controlador2 {

    @Autowired
    PersonService ps;
    @Autowired
    CiudadService cs;

    @GetMapping("/controlador2/getPersona")
    public Persona getPersona(){
        Persona p=ps.getPersona();
        p.setEdad(p.getEdad()*2);
        return p;
    }
    @GetMapping("/controlador2/getCiudades")
    public List<Ciudad> getCiudades(){
        return cs.getCiudades();
    }
}
