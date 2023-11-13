package com.block7crudvalidation.application.impl;

import com.block7crudvalidation.application.interfaces.StudentService;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.AsignaturaOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentFullOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentSimpleOutputDto;
import com.block7crudvalidation.domain.Asignatura;
import com.block7crudvalidation.domain.Mappers.StudentMapper;
import com.block7crudvalidation.domain.Persona;
import com.block7crudvalidation.domain.Profesor;
import com.block7crudvalidation.domain.Student;
import com.block7crudvalidation.repository.AsignaturaRepository;
import com.block7crudvalidation.repository.PersonRepository;
import com.block7crudvalidation.repository.ProfesorRepository;
import com.block7crudvalidation.repository.StudentRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
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
    public StudentSimpleOutputDto addStudent(StudentInputDto Student) {
        //Validación de nulos
        if( Student.getNum_hours_week()==0 ||
            Student.getBranch()==null || Student.getBranch().isBlank()){
            //Lanzo la excepcion para que la recoja el controlador y la maneje con un metodo handler
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY,"Algun/os valores no pueden ser nulos");
        }

        Persona persona=personRepository.findById(Student.getId_persona()).orElseThrow();
        Profesor profesor=profesorRepository.findById(Student.getId_profesor()).orElseThrow();
        if (persona.getProfesor() != null && persona.getProfesor().getId_profesor() != null)throw new NoSuchElementException("Esta persona es un profesor");
        if (persona.getStudent() != null && persona.getStudent().getId_student() != null)throw new NoSuchElementException("Esta persona ya tiene un estudiante asignado");

        StudentMapper studentMapper=StudentMapper.INSTANCE;
        Student newStudent=studentMapper.studentInputDtoToStudent(Student);
        //persona.setStudent(newStudent);
        newStudent.setPersona(persona);
        newStudent.setProfesor(profesor);
        return studentRepository.save(newStudent)
                .studentToStudentSimpleOutputDto();
    }

    @Override
    public StudentFullOutputDto getFullStudentById(String id) {
        return studentRepository.findById(id).orElseThrow(()->new NoSuchElementException("id no encontrado del estudiante "+id)).studentToStudentFulltOutputDto();
    }

    @Override
    public StudentSimpleOutputDto getSimpleStudentById(String id) {
        return studentRepository.findById(id).orElseThrow(()->new NoSuchElementException("id no encontrado del estudiante "+id)).studentToStudentSimpleOutputDto();
    }

    @Override
    public void deleteStudentById(String id) {
        Student student = studentRepository.findById(id).orElseThrow(()->new NoSuchElementException("id no encontrado del estudiante "+id));
        Set<Asignatura> asignaturaList = student.getAsignaturas();
        for(Asignatura asignatura: asignaturaList){
            asignatura.getStudents().remove(student);
            asignaturaRepository.save(asignatura);
        }
        Profesor profesor=profesorRepository.findById(student.getProfesor().getId_profesor()).orElseThrow(()->new NoSuchElementException("id no encontrado del profesor "+id));
        profesor.getStudents().remove(student);
        profesorRepository.save(profesor);
        studentRepository.deleteById(id);
        Persona persona=personRepository.findById(student.getPersona().getId_persona()).orElseThrow(()->new NoSuchElementException("id no encontrado de la persona "+id));
        persona.setStudent(null);
        personRepository.save(persona);
    }

    @Override
    public List<StudentSimpleOutputDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(Student::studentToStudentSimpleOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentSimpleOutputDto updateStudent(StudentInputDto Student) {
        Student s=studentRepository.findById(Student.getId_persona()).orElseThrow();
        StudentMapper.INSTANCE.updateStudentFromDto(Student,s);
        return studentRepository.save(s).studentToStudentSimpleOutputDto();
    }

    public List<AsignaturaOutputDto> getAsignaturasStudent(String id){
        Student s=studentRepository.findById(id).orElseThrow();
        return s.getAsignaturas().stream().map(Asignatura::AsignaturaToAsignaturaOutputDto).collect(Collectors.toList());
    }

    public StudentFullOutputDto addAsignaturasEstudiante(List<String> idAsignaturas, String idStudent){
        Student s=studentRepository.findById(idStudent).orElseThrow();
        Set<Asignatura> lista=s.getAsignaturas();
        for(String idAsignatura: idAsignaturas){
            lista.add(asignaturaRepository.findById(idAsignatura).orElseThrow());
        }
        s.setAsignaturas(lista);
        return studentRepository.save(s).studentToStudentFulltOutputDto();
    }

    public StudentFullOutputDto removeAsignaturasEstudiante(List<String> idAsignaturas, String id) {
        Student s=studentRepository.findById(id).orElseThrow();
        Set<Asignatura> lista=s.getAsignaturas();
        for(String idAsignatura: idAsignaturas){
            lista.remove(asignaturaRepository.findById(idAsignatura).orElseThrow(()-> new NoSuchElementException("El Id de asignatura no existe")));
        }
        s.setAsignaturas(lista);
        return studentRepository.save(s).studentToStudentFulltOutputDto();
    }
}
