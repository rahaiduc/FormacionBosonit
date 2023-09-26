package com.block7crud.application;

import com.block7crud.controller.NoSuchElementFoundException;
import com.block7crud.controller.dto.PersonInputDto;
import com.block7crud.controller.dto.PersonOutputDto;
import com.block7crud.domain.Persona;
import com.block7crud.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    PersonRepository personRepository;

    @Override
    public PersonOutputDto addPerson(PersonInputDto person) {

        return personRepository.save(new Persona(person))
                .personToPersonOutputDto();
    }
    @Override
    public PersonOutputDto getPersonById(int id){
        return personRepository.findById(id).orElseThrow()
                .personToPersonOutputDto();
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
    @Override
    public PersonOutputDto updatePerson(PersonInputDto person) {
        personRepository.findById(person.getId()).orElseThrow();
        return personRepository.save(new Persona(person))
                .personToPersonOutputDto();
    }
}