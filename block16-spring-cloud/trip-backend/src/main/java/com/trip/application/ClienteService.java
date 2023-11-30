package com.trip.application;

import com.trip.domain.Cliente;
import com.trip.domain.Viaje;
import com.trip.domain.dto.input.ClienteInputDto;
import com.trip.domain.dto.output.ClienteOutputDto;
import com.trip.domain.dto.output.ViajeOutputDto;
import com.trip.domain.mappers.ClienteMapper;
import com.trip.repository.ClienteRepository;
import com.trip.repository.ViajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ViajeRepository viajeRepository;


    public ClienteOutputDto addCliente(ClienteInputDto clienteInputDto) {
       return null;
    }

    public void deleteCliente(int id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontró el cliente con ID: " + id));
        cliente.getViajes().forEach(viaje -> {
            viaje.getPasajeros().remove(cliente);
            viajeRepository.save(viaje);
        });
        clienteRepository.deleteById(id);
    }


    public ClienteOutputDto updateCliente(Integer id, ClienteInputDto clienteInputDto) {
        return null;
    }


    public List<ClienteOutputDto> getAllCliente(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return clienteRepository.findAll(pageRequest).getContent()
                .stream()
                .map(Cliente::clienteToClienteOutput).toList();
    }


    public ClienteOutputDto getCliente(int id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontró el cliente con ID: " + id));
        return cliente.clienteToClienteOutput();
    }


    public int countPasajeros(int idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje).orElseThrow(() -> new NoSuchElementException("No se encontró el viaje con Id " + idViaje));
        return viaje.getPasajeros().size();
    }
}
