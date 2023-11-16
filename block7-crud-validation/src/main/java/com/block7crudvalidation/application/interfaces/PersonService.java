package com.block7crudvalidation.application.interfaces;

import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;
import com.block7crudvalidation.domain.Persona;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PersonService {
    PersonOutputDto addPerson(PersonInputDto person);
    PersonOutputDto getPersonById(String id);
    void deletePersonById(String id);
    Iterable<PersonOutputDto> getAllPersons(int pageNumber, int pageSize);
    PersonOutputDto updatePerson(PersonInputDto person);

    Persona authenticate(String usuario, String password);
}
