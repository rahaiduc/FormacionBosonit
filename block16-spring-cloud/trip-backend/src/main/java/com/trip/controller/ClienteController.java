package com.trip.controller;
import com.trip.application.ClienteService;
import com.trip.domain.dto.input.ClienteInputDto;
import com.trip.domain.dto.output.ClienteOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @PostMapping
    public ClienteOutputDto addCliente(@RequestBody ClienteInputDto clienteInputDto){
        return clienteService.addCliente(clienteInputDto);
    }

    @GetMapping("/{id}")
    public ClienteOutputDto getCliente(@PathVariable int id) {
        return clienteService.getCliente(id);
    }

    @DeleteMapping
    public String deleteCliente(@RequestParam int id){
        clienteService.deleteCliente(id);
        return "El cliente con id: "+id+" ha sido eliminado";
    }
    @GetMapping
    public Iterable<ClienteOutputDto> getAllCliente(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return clienteService.getAllCliente(pageNumber, pageSize);
    }

    @PutMapping("/{id}")
    public ClienteOutputDto updateCliente(@PathVariable Integer id, @RequestBody ClienteInputDto clienteInputDto){
        return clienteService.updateCliente(id, clienteInputDto);
    }

    // Otros metodos

    @GetMapping("/count/{idViaje}")
    public Integer countPasajeros(@PathVariable Integer idViaje){
        return clienteService.countPasajeros(idViaje);
    }
}
