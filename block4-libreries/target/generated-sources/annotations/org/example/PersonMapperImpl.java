package org.example;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-13T10:36:29+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonaDTO toPersonDTO(Persona Person) {
        if ( Person == null ) {
            return null;
        }

        PersonaDTO personaDTO = new PersonaDTO();

        personaDTO.setName( Person.getName() );
        personaDTO.setDni( Person.getDni() );

        return personaDTO;
    }
}
