package com.block7crudvalidation.controller;

import com.block7crudvalidation.application.impl.AsignaturaServiceImpl;
import com.block7crudvalidation.application.impl.StudentServiceImpl;
import com.block7crudvalidation.controller.dto.inputs.AsignaturaInputDto;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.AsignaturaOutputDto;
import com.block7crudvalidation.domain.CustomError;
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
@RequestMapping("/asignatura")
public class ControllerAsignatura {
    @Autowired
    AsignaturaServiceImpl asignaturaService;

    @PostMapping
    public ResponseEntity<AsignaturaOutputDto> addAsignatura(@Valid @RequestBody AsignaturaInputDto asignatura) {
        try {
            URI location = URI.create("/asignatura");
            return ResponseEntity.created(location).body(asignaturaService.addAsignatura(asignatura));
        }
        catch (Exception e){
            //Tengo un metodo handler que maneja la excepcion
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaOutputDto> getAsignaturaById(@PathVariable String id) {
        try {
            return ResponseEntity.ok().body(asignaturaService.getAsignaturaById(id));
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

   /* @GetMapping("/nombre/{nombre}")
    public List<Persona> getPersonByName(@PathVariable String nombre) {
        try {
            return studentService.getPersonByName(nombre);
        } catch (Exception e) {
            throw e;
        }
    }*/

    @GetMapping
    public List<AsignaturaOutputDto> getAllAsignaturas() {
        try{
            return asignaturaService.getAllAsignaturas();
        }catch (Exception e){
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAsignaturaById(@PathVariable String id) {
        try {
            asignaturaService.deleteAsignaturaById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignaturaOutputDto> updateAsignatura(@PathVariable String id,@RequestBody AsignaturaInputDto asignatura) {
        try {
            asignatura.setId_asignatura(id);
            return ResponseEntity.ok().body(asignaturaService.updateAsignatura(asignatura));
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
        NoSuchElementException ne=new NoSuchElementException("404-Asignatura no encontrada");
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
        ce.setMensaje("Error 404 - Asignatura no encontrada");
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
