package org.example;


import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {


    PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );

    @Mapping(source = "name", target = "name")
    @Mapping(source = "dni", target = "dni")
    PersonaDTO toPersonDTO(Persona Person);
}
