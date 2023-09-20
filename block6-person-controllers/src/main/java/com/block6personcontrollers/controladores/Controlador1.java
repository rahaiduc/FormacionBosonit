package com.block6personcontrollers.controladores;

import com.block6personcontrollers.modelos.Ciudad;
import com.block6personcontrollers.modelos.Persona;
import com.block6personcontrollers.servicios.CiudadService;
import com.block6personcontrollers.servicios.PersonService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class Controlador1 {

    @Autowired
    PersonService ps;
    @Autowired
    CiudadService cs;

    @Autowired
    @Qualifier("bean1")
    private Persona bean1;

    @Autowired
    @Qualifier("bean2")
    private Persona bean2;

    @Autowired
    @Qualifier("bean3")
    private Persona bean3;



    @GetMapping("/controlador1/addPersona")
    public Persona addpersona(@RequestHeader Map<String, String> headers){
        return ps.devolverPersona(headers.get("nombre"),headers.get("poblacion"),Integer.parseInt(headers.get("edad")));
    }

    @PostMapping("/controlador1/addCiudad")
    public void addCiudad(@RequestHeader Map<String, String> headers){
        cs.addCiudad(new Ciudad(headers.get("nombre"),Integer.parseInt(headers.get("habitantes"))));
    }

    @GetMapping("/controlador/bean/{bean}")
    public Persona getBean(@PathVariable("bean") String param){
        switch (param) {
            case "bean1":
                return bean1;
            case "bean2":
                return bean2;
            case "bean3":
                return bean3;
        }
        return null;

    }
}
