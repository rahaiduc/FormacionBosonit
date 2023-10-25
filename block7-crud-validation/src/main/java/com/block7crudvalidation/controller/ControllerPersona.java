package com.block7crudvalidation.controller;

import com.block7crudvalidation.application.impl.PersonServiceImpl;
import com.block7crudvalidation.application.interfaces.UserClient;
import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;
import com.block7crudvalidation.controller.dto.outputs.ProfesorOutputDto;
import com.block7crudvalidation.domain.CustomError;
import com.block7crudvalidation.domain.Persona;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.util.*;

@RestController
public class ControllerPersona {

    @Autowired
    PersonServiceImpl personService;

    @Autowired
    UserClient userClient;

    @PostMapping("person")
    public ResponseEntity<PersonOutputDto> addPerson(@Valid @RequestBody PersonInputDto person) {
        try {
            URI location = URI.create("/persona");
            return ResponseEntity.created(location).body(personService.addPerson(person));
        }
        catch (Exception e){
            //Tengo un metodo handler que maneja la excepcion
            throw e;
        }
    }
    @CrossOrigin(origins = "https://codepen.io/")
    @PostMapping("/addperson")
    public ResponseEntity<PersonOutputDto> añadirPersonaCors(@RequestBody PersonInputDto persona){
        URI location = URI.create("/persona");
        return ResponseEntity.created(location).body(personService.addPerson(persona));
    }

    @CrossOrigin(origins = "https://codepen.io/")
    @GetMapping("/getall")
    public ResponseEntity<List<PersonOutputDto>> getAllCors() {
        return ResponseEntity.ok().body(personService.getAllPersons());
    }

    @GetMapping("person/{id}")
    public PersonOutputDto getPersonById(@PathVariable String id) {
        try {
            return personService.searchPersonById(id);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("person/nombre/{nombre}")
    public List<Persona> getPersonByName(@PathVariable String nombre) {
        try {
            return personService.getPersonByName(nombre);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("person/profesor/{id}")
    public ProfesorOutputDto getProfesorOutputById(@PathVariable int id){
        return userClient.getProfesor(id);
    }

    @GetMapping("person/getall")
    public List<PersonOutputDto> getAllPerson() {
        try{
            return personService.getAllPersons();
        }catch (Exception e){
            throw e;
        }
    }

    @DeleteMapping("person/{id}")
    public ResponseEntity<PersonOutputDto> deletePersonById(@PathVariable String id) {
        try {
            PersonOutputDto pod=personService.getPersonById(id);
            personService.deletePersonById(id);
            return ResponseEntity.ok().body(pod);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("person/{id}")
    public ResponseEntity<PersonOutputDto> updatePerson(@PathVariable String id,@RequestBody PersonInputDto person) {
        try {
            person.setId_persona(id);
            return ResponseEntity.ok().body(personService.updatePerson(person));
        } catch (Exception e) {
            throw e;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomError> handleEntityNotFoundException() {
        CustomError ce = new CustomError();
        ce.setTimestamp(new Date());
        ce.setHttpCode(HttpStatus.NOT_FOUND.value());
        ce.setMensaje("Error 404 - Persona no encontrada");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ce);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<CustomError> handleUnprocessableEntity(HttpClientErrorException hee) {
        CustomError ce = new CustomError();
        ce.setTimestamp(new Date());
        ce.setHttpCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        ce.setMensaje(hee.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ce);
    }



}

