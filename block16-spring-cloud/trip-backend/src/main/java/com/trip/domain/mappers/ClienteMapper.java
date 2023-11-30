package com.trip.domain.mappers;

import com.trip.domain.Cliente;
import com.trip.domain.dto.input.ClienteInputDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteMapper INSTANCE=Mappers.getMapper(ClienteMapper.class);
    Cliente clienteInputToCliente(ClienteInputDto clienteInputDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePatchCliente(ClienteInputDto dto, @MappingTarget Cliente entity);
}
