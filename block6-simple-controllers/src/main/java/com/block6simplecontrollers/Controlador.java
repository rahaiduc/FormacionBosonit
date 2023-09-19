package com.block6simplecontrollers;

import com.github.cliftonlabs.json_simple.Jsoner;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controlador {

    @GetMapping("/user/{nombre}")
    public String getNombre(@PathVariable String nombre){
        return "Hola "+ nombre;
    }

    @PostMapping ("/useradd")
    public Persona postPersona(@RequestBody Persona per){
        return new Persona(per.getNombre(), per.getPoblacion(),per.getEdad()+1);

    }

}
