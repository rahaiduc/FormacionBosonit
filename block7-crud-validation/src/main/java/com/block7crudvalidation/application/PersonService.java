package com.block7crudvalidation.application;

import com.block7crudvalidation.controller.dto.PersonInputDto;
import com.block7crudvalidation.controller.dto.PersonOutputDto;
import com.block7crudvalidation.domain.Persona;

public interface PersonService {
    PersonOutputDto addPerson(PersonInputDto person);
    PersonOutputDto getPersonById(int id);
    void deletePersonById( int id);
    Iterable<PersonOutputDto> getAllPersons(int pageNumber, int pageSize);
    PersonOutputDto updatePerson(PersonInputDto person);
}
