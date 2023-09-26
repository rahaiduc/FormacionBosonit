package com.block7crud.controller;

import com.block7crud.application.PersonServiceImpl;
import com.block7crud.controller.dto.PersonInputDto;
import com.block7crud.controller.dto.PersonOutputDto;
import com.block7crud.domain.Persona;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("persona")
public class Controller1 {

    @Autowired
    PersonServiceImpl personService;

    @PostMapping
    public ResponseEntity<PersonOutputDto> addPerson(@Valid @RequestBody PersonInputDto person) {
        URI location = URI.create("/persona");
        return ResponseEntity.created(location).body(personService.addPerson(person));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonOutputDto> getPersonById(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(personService.getPersonById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletePersonById(@RequestParam int id) {
        try {
            personService.deletePersonById(id);
            return ResponseEntity.ok().body("person with id: "+id+" was deleted");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public Iterable<PersonOutputDto> getAllPerson(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {

        return personService.getAllPersons(pageNumber, pageSize);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonOutputDto> updatePerson(@PathVariable int id,@RequestBody JSONObject json) {
        try {
            PersonOutputDto per=personService.getPersonById(id);
            String name=(String) json.get("name")!=null ? (String)json.get("name") : per.getName();
            String edad=(String) json.get("edad")!=null ? (String)json.get("edad") : per.getEdad();
            String poblacion=(String) json.get("poblacion")!=null ? (String)json.get("poblacion") : per.getPoblacion();
            PersonInputDto persona=new PersonInputDto(id,name,edad,poblacion);
            System.out.println("hola"+id+name+edad+poblacion);
            return ResponseEntity.ok().body(personService.updatePerson(persona));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException() {
        NoSuchElementException ne=new NoSuchElementException("404-Persona no encontrada");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ne.getMessage());
    }


}

