package com.block7crudvalidation.application.impl;

import com.block7crudvalidation.application.interfaces.ProfesorService;
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
    @Override
    public ProfesorOutputDto addProfesor(ProfesorInputDto Profesor) {
        if( Profesor.getBranch()==null || Profesor.getBranch().isBlank()){
            //Lanzo la excepcion para que la recoja el controlador y la maneje con un metodo handler
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY,"Algun/os valores no pueden ser nulos");
        }
        Profesor newProfesor=ProfesorMapper.INSTANCE.profesorInputDtoToProfesor(Profesor);
        return profesorRepository.save(newProfesor).ProfesorToProfesorOutputDto();
    }

    @Override
    public ProfesorOutputDto getProfesorById(String id) {
        return profesorRepository.findById(id).orElseThrow().ProfesorToProfesorOutputDto();
    }

    @Override
    public void deleteProfesorById(String id) {
        profesorRepository.findById(id).orElseThrow();
        profesorRepository.deleteById(id);
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
        Profesor p=profesorRepository.findById(Profesor.getId_profesor()).orElseThrow();
        ProfesorMapper.INSTANCE.updateProfesorFromDto(Profesor,p);
        return profesorRepository.save(p)
                .ProfesorToProfesorOutputDto();
    }
}
