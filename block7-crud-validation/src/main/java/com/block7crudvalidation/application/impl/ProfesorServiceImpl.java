package com.block7crudvalidation.application.impl;

import com.block7crudvalidation.application.interfaces.ProfesorService;
import com.block7crudvalidation.application.interfaces.StudentService;
import com.block7crudvalidation.controller.dto.inputs.ProfesorInputDto;
import com.block7crudvalidation.controller.dto.outputs.ProfesorOutputDto;
import com.block7crudvalidation.domain.Mappers.PersonMapper;
import com.block7crudvalidation.domain.Mappers.ProfesorMapper;
import com.block7crudvalidation.domain.Persona;
import com.block7crudvalidation.domain.Profesor;
import com.block7crudvalidation.domain.Student;
import com.block7crudvalidation.repository.AsignaturaRepository;
import com.block7crudvalidation.repository.PersonRepository;
import com.block7crudvalidation.repository.ProfesorRepository;
import com.block7crudvalidation.repository.StudentRepository;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    @Autowired
    StudentService studentService;
    @Override
    public ProfesorOutputDto addProfesor(ProfesorInputDto profesorInputDto) {
        if( profesorInputDto.getBranch()==null || profesorInputDto.getBranch().isBlank()){
            //Lanzo la excepcion para que la recoja el controlador y la maneje con un metodo handler
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY,"Algun/os valores no pueden ser nulos");
        }
        Persona persona=personRepository.findById(profesorInputDto.getId_persona()).orElseThrow(()-> new NoSuchElementException("404-Persona no existe"));
        if (persona.getProfesor() != null && persona.getProfesor().getId_profesor() != null)throw new NoSuchElementException("Esta persona ya tiene un profesor asignado");
        if (persona.getStudent() != null && persona.getStudent().getId_student() != null)throw new NoSuchElementException("Esta persona es un estudiante");

        Profesor newProfesor=ProfesorMapper.INSTANCE.profesorInputDtoToProfesor(profesorInputDto);
        newProfesor.setPersona(persona);
       // persona.setProfesor(newProfesor);
        return profesorRepository.save(newProfesor).ProfesorToProfesorOutputDto();
    }

    @Override
    public ProfesorOutputDto getProfesorById(String id) {
        return profesorRepository.findById(id).orElseThrow(()->new NoSuchElementException("404 - No existe el profesor")).ProfesorToProfesorOutputDto();
    }

    @Override
    public void deleteProfesorById(String id) {
        Profesor profesor = profesorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("404 - No existe el profesor"));
        Persona persona=personRepository.findById(profesor.getPersona().getId_persona()).orElseThrow();
        for(Student student: profesor.getStudents()){
            studentService.deleteStudentById(student.getId_student());
        }
        persona.setProfesor(null);
        personRepository.save(persona);
    }

    @Override
    public List<ProfesorOutputDto> getAllProfesors() {
        return profesorRepository.findAll()
                .stream()
                .map(Profesor::ProfesorToProfesorOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProfesorOutputDto updateProfesor(ProfesorInputDto Profesor) {
        Profesor p=profesorRepository.findById(Profesor.getId_profesor()).orElseThrow(() -> new NoSuchElementException("404 - No existe el profesor"));
        ProfesorMapper.INSTANCE.updateProfesorFromDto(Profesor,p);
        return profesorRepository.save(p)
                .ProfesorToProfesorOutputDto();
    }
}
