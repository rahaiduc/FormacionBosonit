package com.block7crudvalidation.application.impl;

import com.block7crudvalidation.application.interfaces.PersonService;
import com.block7crudvalidation.application.interfaces.ProfesorService;
import com.block7crudvalidation.application.interfaces.StudentService;
import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;
import com.block7crudvalidation.domain.Mappers.PersonMapper;
import com.block7crudvalidation.domain.Persona;
import com.block7crudvalidation.repository.PersonRepository;
import com.block7crudvalidation.repository.PersonRepositoryCustom;
import com.block7crudvalidation.repository.ProfesorRepository;
import com.block7crudvalidation.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    ProfesorService profesorService;
    @Autowired
    AuthenticationManager authenticationManager;

    String noEncontrado;

    @Override
    public PersonOutputDto addPerson(PersonInputDto person) {
        //Validación de nulos
        if (person.getName() == null || person.getName().isBlank() ||
                person.getCompany_email() == null || person.getCompany_email().isBlank() ||
                person.getUsuario() == null || person.getUsuario().isBlank() ||
                person.getPersonal_email() == null || person.getPersonal_email().isBlank() ||
                person.getCity() == null || person.getCity().isBlank() ||
                person.getCreated_date() == null ||
                person.getPassword() == null || person.getPassword().isBlank()) {
            //Lanzo la excepcion para que la recoja el controlador y la maneje con un metodo handler
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Algun/os valores no pueden ser nulos");
        }
        //Validacion caracteres en el campo usuario
        if (person.getUsuario().length() > 10 || person.getUsuario().length() < 6) {
            //Lanzo la excepcion para que la recoja el controlador y la maneje con un metodo handler
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "El usuario tiene que tener entre 6 y 10 caracteres");
        }
        return personRepository.save(new Persona(person))
                .personToPersonOutputDto();
    }

    @Override
    public PersonOutputDto getPersonById(String id) {
        return personRepository.findById(id).orElseThrow(() -> new NoSuchElementException(noEncontrado))
                .personToPersonOutputDto();
    }

    public PersonOutputDto searchPersonById(String id) {
        Persona p = personRepository.findById(id).orElseThrow(() -> new NoSuchElementException(noEncontrado));
        if (p.getStudent() != null) {
            return p.personToPersonaEstudianteOutputDto();
        } else if (p.getProfesor() != null) {
            return p.personToPersonProfesorOutputDto();
        } else {
            return p.personToPersonOutputDto();
        }
    }


    public List<Persona> getPersonByName(String nombre) {
        return personRepository.findByName(nombre).stream().toList();
    }

    @Override
    public void deletePersonById(String id) {
        Persona persona = personRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(noEncontrado));
        if (persona.getProfesor() != null && persona.getProfesor().getId_profesor() != null)
            profesorService.deleteProfesorById(persona.getProfesor().getId_profesor());
        if (persona.getStudent() != null && persona.getStudent().getId_student() != null)
            studentService.deleteStudentById(persona.getStudent().getId_student());
        personRepository.deleteById(id);
    }

    @Override
    public List<PersonOutputDto> getAllPersons(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Persona::personToPersonOutputDto).toList();
    }

    public List<PersonOutputDto> getAllPersons() {
        return personRepository.findAll().stream().map(Persona::personToPersonOutputDto).collect(Collectors.toList());
    }

    @Override
    public PersonOutputDto updatePerson(PersonInputDto person) {
        Persona p = personRepository.findById(person.getId_persona()).orElseThrow(() -> new NoSuchElementException(noEncontrado));
        PersonMapper.INSTANCE.updatePersonFromDto(person, p);
        return personRepository.save(p)
                .personToPersonOutputDto();
    }

    public List<PersonOutputDto> getCustomQuery(HashMap<String, Object> data) {
        PageRequest pageRequest = PageRequest.of((Integer) data.get("pageNumber"), (Integer) data.get("pageSize"));
        if (data.get("dateCondition").equals("lt")) {
            return personRepository.getLessQuery(data, pageRequest);
        } else {
            return personRepository.getGreaterQuery(data, pageRequest);
        }
    }


    public Persona authenticate(String usuario, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuario,
                        password
                )
        );

        return personRepository.findByUsuario(usuario).orElseThrow(()->new NoSuchElementException("No se ha encontrado el usuario"));
    }
}