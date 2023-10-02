package com.block7crudvalidation.application.impl;

import com.block7crudvalidation.application.interfaces.PersonService;
import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;
import com.block7crudvalidation.domain.Persona;
import com.block7crudvalidation.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public PersonOutputDto addPerson(PersonInputDto person) {
        //Validación de nulos
        if( person.getName()==null || person.getName().isBlank()  ||
            person.getCompany_email()==null || person.getCompany_email().isBlank() ||
            person.getUsuario()==null || person.getUsuario().isBlank()  ||
            person.getPersonal_email()==null || person.getPersonal_email().isBlank() ||
            person.getCity()==null || person.getCity().isBlank() ||
            person.getCreated_date()==null ||
            person.getPassword()==null || person.getPassword().isBlank() ){
            //Lanzo la excepcion para que la recoja el controlador y la maneje con un metodo handler
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY,"Algun/os valores no pueden ser nulos");
        }
        //Validacion caracteres en el campo usuario
        if(person.getUsuario().length()>10 || person.getUsuario().length()<6){
            //Lanzo la excepcion para que la recoja el controlador y la maneje con un metodo handler
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY,"El usuario tiene que tener entre 6 y 10 caracteres");
        }
        return personRepository.save(new Persona(person))
                .personToPersonOutputDto();
    }
    @Override
    public PersonOutputDto getPersonById(int id){
        return personRepository.findById(id).orElseThrow()
                .personToPersonOutputDto();
    }

    public List<Persona> getPersonByName(String nombre){
        return personRepository.findByName(nombre).stream().toList();
    }

    @Override
    public void deletePersonById(int id) {
        personRepository.findById(id).orElseThrow();
        personRepository.deleteById(id);
    }
    @Override
    public List<PersonOutputDto> getAllPersons(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return personRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Persona::personToPersonOutputDto).toList();
    }
    public List<Persona> getAllPersons() {
        return personRepository.findAll().stream().toList();
    }
    @Override
    public PersonOutputDto updatePerson(PersonInputDto person) {
        personRepository.findById(person.getId_persona()).orElseThrow();
        return personRepository.save(new Persona(person))
                .personToPersonOutputDto();
    }
}