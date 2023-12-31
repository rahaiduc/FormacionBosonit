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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
            URI location = URI.create("/persona");
        return ResponseEntity.created(location).body(personService.addPerson(person));
    }
    @CrossOrigin(origins = "https://cdpn.io")
    @PostMapping("/addperson")
    public PersonOutputDto añadirPersonaCors(@RequestBody PersonInputDto persona){
        URI location = URI.create("/persona");
        return personService.addPerson(persona);
    }

    @CrossOrigin(origins = "https://cdpn.io")
    @GetMapping("/getall")
    public List<PersonOutputDto> getAllCors() {
        return personService.getAllPersons();
    }

    @GetMapping("/customQuery")
    public List<PersonOutputDto> customQuery(@RequestParam(required = false) String usuario,@RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String surname,
                                                        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date createdDate,
                                                        @RequestParam(defaultValue = "gt",required = false) String dateCondition,
                                                        @RequestParam(defaultValue = "usuario", required = false) String orderBy,
                                                        @RequestParam(defaultValue = "asc", required = false) String orderByDirection,
                                                        @RequestParam Integer pageNumber,
                                                        @RequestParam(defaultValue = "3", required = false) Integer pageSize){
        HashMap<String, Object> data = new HashMap<>();
        if(usuario != null) data.put("usuario",usuario);
        if(name != null) data.put("name",name);
        if(surname != null) data.put("surname",surname);
        if(createdDate != null) data.put("createdDate",createdDate);
        if(dateCondition != null) data.put("dateCondition",dateCondition);
        if(orderBy != null) data.put("orderBy",orderBy);
        if(orderByDirection != null) data.put("orderByDirection",orderByDirection);
        if(pageNumber != null) data.put("pageNumber",pageNumber);
        if(pageSize != null) data.put("pageSize",pageSize);
        return personService.getCustomQuery(data);
    }


    @GetMapping("person/{id}")
    public PersonOutputDto getPersonById(@PathVariable String id) {
        return personService.searchPersonById(id);
    }

    @GetMapping("person/nombre/{nombre}")
    public List<Persona> getPersonByName(@PathVariable String nombre) {
        return personService.getPersonByName(nombre);
    }

    @GetMapping("person/profesor/{id}")
    public ProfesorOutputDto getProfesorOutputById(@PathVariable int id){
        return userClient.getProfesor(id);
    }

    @GetMapping("person/getall")
    public List<PersonOutputDto> getAllPerson() {
        return personService.getAllPersons();
    }

    @DeleteMapping("person/{id}")
    public ResponseEntity<PersonOutputDto> deletePersonById(@PathVariable String id) {
        PersonOutputDto pod=personService.getPersonById(id);
        personService.deletePersonById(id);
        return ResponseEntity.ok().body(pod);
    }

    @PutMapping("person/{id}")
    public ResponseEntity<PersonOutputDto> updatePerson(@PathVariable String id,@RequestBody PersonInputDto person) {
        person.setId_persona(id);
        return ResponseEntity.ok().body(personService.updatePerson(person));
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomError> handleMissingParametersExceptions(MissingServletRequestParameterException ex) {
        CustomError ce = new CustomError();
        ce.setTimestamp(new Date());
        ce.setHttpCode(HttpStatus.BAD_REQUEST.value());
        ce.setMensaje("Error 400-Faltan parametros por enviar en la Query Params");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ce);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ne) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ne.getMessage());
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

