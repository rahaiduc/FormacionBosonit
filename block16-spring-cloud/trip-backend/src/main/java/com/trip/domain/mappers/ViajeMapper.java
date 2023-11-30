package com.trip.domain.mappers;

import com.trip.domain.Cliente;
import com.trip.domain.Viaje;
import com.trip.domain.dto.input.ClienteInputDto;
import com.trip.domain.dto.input.ViajeInputDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface ViajeMapper {
    ViajeMapper INSTANCE=Mappers.getMapper(ViajeMapper.class);
    Viaje viajeInputToViaje(ViajeInputDto viajeInputDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePatchViaje(ViajeInputDto dto, @MappingTarget Viaje entity);
}
