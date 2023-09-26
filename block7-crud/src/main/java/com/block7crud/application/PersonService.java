package com.block7crud.application;

import com.block7crud.controller.dto.PersonInputDto;
import com.block7crud.controller.dto.PersonOutputDto;

public interface PersonService {
    PersonOutputDto addPerson(PersonInputDto person);
    PersonOutputDto getPersonById(int id);
    void deletePersonById( int id);
    Iterable<PersonOutputDto> getAllPersons(int pageNumber, int pageSize);
    PersonOutputDto updatePerson(PersonInputDto person);
}
