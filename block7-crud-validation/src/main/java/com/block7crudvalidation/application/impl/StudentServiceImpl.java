package com.block7crudvalidation.application.impl;

import com.block7crudvalidation.application.interfaces.StudentService;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentSimpleOutputDto;
import com.block7crudvalidation.domain.Mappers.StudentMapper;
import com.block7crudvalidation.domain.Persona;
import com.block7crudvalidation.domain.Student;
import com.block7crudvalidation.repository.AsignaturaRepository;
import com.block7crudvalidation.repository.PersonRepository;
import com.block7crudvalidation.repository.ProfesorRepository;
import com.block7crudvalidation.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Override
    public StudentOutputDto addStudent(StudentInputDto Student) {
        //Validación de nulos
        if( Student.getNum_hours_week()==0 ||
            Student.getBranch()==null || Student.getBranch().isBlank()){
            //Lanzo la excepcion para que la recoja el controlador y la maneje con un metodo handler
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY,"Algun/os valores no pueden ser nulos");
        }

        Persona persona=personRepository.findById(Student.getId_persona()).orElseThrow();
        if (persona.getProfesor() != null && persona.getProfesor().getId_profesor() != null)throw new NoSuchElementException("Esta persona ya tiene un profesor asignado");
        if (persona.getStudent() != null && persona.getStudent().getId_student() != null)throw new NoSuchElementException("Esta persona ya tiene un profesor asignado");

        StudentMapper studentMapper=StudentMapper.INSTANCE;
        Student newStudent=studentMapper.studentInputDtoToStudent(Student);
        persona.setStudent(newStudent);
        newStudent.setPersona(persona);
        return studentRepository.save(newStudent)
                .studentToStudentOutputDto();
    }

    @Override
    public StudentOutputDto getFullStudentById(String id) {
        return studentRepository.findById(id).orElseThrow().studentToStudentOutputDto();
    }

    @Override
    public StudentSimpleOutputDto getSimpleStudentById(String id) {
        return studentRepository.findById(id).orElseThrow().studentToStudentSimpleOutputDto();
    }

    @Override
    public void deleteStudentById(String id) {
        studentRepository.findById(id).orElseThrow();
        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentOutputDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(Student::studentToStudentOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentOutputDto updateStudent(StudentInputDto Student) {
        Student s=studentRepository.findById(Student.getId_persona()).orElseThrow();
        StudentMapper.INSTANCE.updateStudentFromDto(Student,s);
        return studentRepository.save(s).studentToStudentOutputDto();
    }
}