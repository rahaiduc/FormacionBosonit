package com.trip.application;

import com.trip.domain.Cliente;
import com.trip.domain.Viaje;
import com.trip.domain.dto.input.ViajeInputDto;
import com.trip.domain.dto.output.ViajeOutputDto;
import com.trip.domain.mappers.ClienteMapper;
import com.trip.domain.mappers.ViajeMapper;
import com.trip.repository.ClienteRepository;
import com.trip.repository.ViajeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


import java.util.*;
import java.util.stream.Collectors;
@Service
public class ViajeService {
    @Autowired
    ViajeRepository viajeRepository;

    @Autowired
    ClienteRepository clienteRepository;



    public ViajeOutputDto addViaje(ViajeInputDto viajeInputDto) {
        if(viajeInputDto.getOrigen().isBlank() || viajeInputDto.getDestino().isBlank() || viajeInputDto.getFechaLlegada().isBlank() ||viajeInputDto.getFechaSalida().isBlank()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Falta datos por introducir");
        }
        Viaje viaje= ViajeMapper.INSTANCE.viajeInputToViaje(viajeInputDto);
        return viajeRepository.save(viaje).viajeToViajeOutput();
    }


    public void deleteViaje(int id) {
        Viaje viaje = viajeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontró el viaje con ID: " + id));
        viaje.getPasajeros().forEach(cliente -> {
            cliente.getViajes().remove(viaje);
            clienteRepository.save(cliente);
        });
        viajeRepository.deleteById(id);
    }


    public ViajeOutputDto updateViaje(Integer id, ViajeInputDto viajeInputDto) {
        Viaje viaje=viajeRepository.findById(id).orElseThrow(()->new NoSuchElementException("No se encontró el viaje con ID: " + id));
        ViajeMapper.INSTANCE.updatePatchViaje(viajeInputDto,viaje);
        return viajeRepository.save(viaje).viajeToViajeOutput();
    }


    public List<ViajeOutputDto> getAllViaje(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return viajeRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Viaje::viajeToViajeOutput).toList();
    }


    public ViajeOutputDto getViaje(int id) {
        Viaje Viaje = viajeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontró el viaje con ID: " + id));
        return Viaje.viajeToViajeOutput();
    }


    public ViajeOutputDto addPasajero(Integer idViaje, Integer idPasajero) {
        Cliente c=clienteRepository.findById(idPasajero).orElseThrow(()->new NoSuchElementException("No se existe el pasajero con el id "+idPasajero));
        Viaje v=viajeRepository.findById(idViaje).orElseThrow(()-> new NoSuchElementException("No existe el viaje con el id "+ idViaje));
        if(verifyViaje(idViaje)){
            List<Cliente> pasajeros=v.getPasajeros();
            pasajeros.add(c);
            v.setPasajeros(pasajeros);
        } else{
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"El viaje no esta disponible porque no esta abierto o se ha alcanzado el limite de pasajeros");
        }
        return  v.viajeToViajeOutput();
    }


    public ViajeOutputDto modifyEstado(Integer idViaje, String estado) {
        Viaje viaje = viajeRepository.findById(idViaje).orElseThrow(() -> new NoSuchElementException("No se encontró el Viaje con ID: " + idViaje));
        viaje.setEstado(estado);
        return viajeRepository.save(viaje).viajeToViajeOutput();
    }


    public boolean verifyViaje(Integer idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje).orElseThrow(() -> new NoSuchElementException("No se encontró el Viaje con ID: " + idViaje));
        return viaje.getEstado().equals("abierto") && viaje.getPasajeros().size() < 40;
    }
}
