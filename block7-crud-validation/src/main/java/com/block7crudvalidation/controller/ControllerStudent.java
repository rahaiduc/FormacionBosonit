package com.block7crudvalidation.controller;

import com.block7crudvalidation.application.impl.PersonServiceImpl;
import com.block7crudvalidation.application.impl.StudentServiceImpl;
import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentFullOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentSimpleOutputDto;
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
@RequestMapping("/estudiante")
public class ControllerStudent {

    @Autowired
    StudentServiceImpl studentService;

    @PostMapping
    public ResponseEntity<StudentSimpleOutputDto> addStudent(@Valid @RequestBody StudentInputDto student) {
        try {
            URI location = URI.create("/estudiante");
            return ResponseEntity.created(location).body(studentService.addStudent(student));
        }
        catch (Exception e){
            //Tengo un metodo handler que maneja la excepcion
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable String id, @RequestParam(value = "outputType",defaultValue = "simple") String outputType) {
        try {
            if(outputType.equals("full")){
                return ResponseEntity.ok().body(studentService.getFullStudentById(id));
            }else{
                return ResponseEntity.ok().body(studentService.getSimpleStudentById(id));
            }
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
    public List<StudentSimpleOutputDto> getAllStudents() {
        try{
            return studentService.getAllStudents();
        }catch (Exception e){
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable String id) {
        try {
            studentService.deleteStudentById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentSimpleOutputDto> updateStudent(@PathVariable String id,@RequestBody StudentInputDto student) {
        try {
            student.setId_student(id);
            return ResponseEntity.ok().body(studentService.updateStudent(student));
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/addAsignaturas/{id}")
    public StudentFullOutputDto addAsignaturas(@PathVariable String id,@RequestBody List<String> idAsignaturas){
        return studentService.addAsignaturasEstudiante(idAsignaturas,id);
    }

    @PutMapping("/removeAsignaturas/{id}")
    public StudentFullOutputDto removeAsignaturas(@PathVariable String id,@RequestBody List<String> idAsignaturas){
        return studentService.removeAsignaturasEstudiante(idAsignaturas,id);
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
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException nsee) {
        NoSuchElementException ne=new NoSuchElementException("404-Estudiante no encontrado");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(nsee.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomError> handleEntityNotFoundException() {
        CustomError ce = new CustomError();
        ce.setTimestamp(new Date());
        ce.setHttpCode(HttpStatus.NOT_FOUND.value());
        ce.setMensaje("Error 404 - Estudiante no encontrado");
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
