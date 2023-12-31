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
            URI location = URI.create("/estudiante");
            return ResponseEntity.created(location).body(studentService.addStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable String id, @RequestParam(value = "outputType",defaultValue = "simple") String outputType) {
            if(outputType.equals("full")){
                return ResponseEntity.ok().body(studentService.getFullStudentById(id));
            }else{
                return ResponseEntity.ok().body(studentService.getSimpleStudentById(id));
            }
    }



    @GetMapping
    public List<StudentSimpleOutputDto> getAllStudents() {
            return studentService.getAllStudents();
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable String id) {
            studentService.deleteStudentById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentSimpleOutputDto> updateStudent(@PathVariable String id,@RequestBody StudentInputDto studentInputDto) {
            studentInputDto.setId_student(id);
            return ResponseEntity.ok().body(studentService.updateStudent(studentInputDto));
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
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(nsee.getMessage());
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
