package com.trip.application;

import com.trip.domain.Cliente;
import com.trip.domain.Viaje;
import com.trip.domain.dto.input.ViajeInputDto;
import com.trip.domain.dto.output.ViajeOutputDto;
import com.trip.repository.ClienteRepository;
import com.trip.repository.ViajeRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;
@Service
public class ViajeService {
    @Autowired
    ViajeRepository viajeRepository;

    @Autowired
    ClienteRepository clienteRepository;



    public ViajeOutputDto addViaje(ViajeInputDto viajeInputDto) {
        return null;
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
        return null;
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
        return null;
    }


    public ViajeOutputDto modifyEstado(Integer idViaje, String estado) {
        Viaje viaje = viajeRepository.findById(idViaje).orElseThrow(() -> new NoSuchElementException("No se encontró el Viaje con ID: " + idViaje));
        viaje.setEstado(estado);
        return viajeRepository.save(viaje).viajeToViajeOutput();
    }


    public String verifyViaje(Integer idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje).orElseThrow(() -> new NoSuchElementException("No se encontró el Viaje con ID: " + idViaje));
        if(viaje.getEstado().equals("abierto") && viaje.getPasajeros().size()<40){
            return "Disponible";
        }
        return "No disponible";
    }
}
