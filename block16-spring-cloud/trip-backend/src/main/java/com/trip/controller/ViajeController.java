package com.trip.controller;

import com.trip.application.ViajeService;
import com.trip.domain.dto.input.ViajeInputDto;
import com.trip.domain.dto.output.ViajeOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viaje")
public class ViajeController {
    @Autowired
    ViajeService viajeService;

    @PostMapping
    public ViajeOutputDto addviaje(@RequestBody ViajeInputDto viajeInputDto){
        return viajeService.addViaje(viajeInputDto);
    }

    @GetMapping("/{id}")
    public ViajeOutputDto getviaje(@PathVariable int id) {
        return viajeService.getViaje(id);
    }

    @DeleteMapping
    public String deleteviaje(@RequestParam int id){
        viajeService.deleteViaje(id);
        return "El viaje con id: "+id+" ha sido eliminado";
    }
    @GetMapping
    public Iterable<ViajeOutputDto> getAllviaje(
            @RequestParam(defaultValue = "0", required = false) int pageNumber,
            @RequestParam(defaultValue = "4", required = false) int pageSize) {
        return viajeService.getAllViaje(pageNumber, pageSize);
    }

    @PutMapping("/{id}")
    public ViajeOutputDto updateviaje(@PathVariable Integer id, @RequestBody ViajeInputDto viajeInputDto){
        return viajeService.updateViaje(id, viajeInputDto);
    }

    // Otros metodos

    @PostMapping("/addPasajero/{idViaje}/{idPasajero}")
    public ViajeOutputDto addPasajero(@PathVariable Integer idViaje, @PathVariable Integer idPasajero){
        return viajeService.addPasajero(idViaje,idPasajero);
    }

    @PutMapping("/{idViaje}/{estado}")
    public ViajeOutputDto modifyStatus(@PathVariable Integer idViaje, @PathVariable String estado){
        return viajeService.modifyEstado(idViaje,estado);
    }

    @GetMapping("/verify/{idViaje}")
    public boolean verifyViaje(@PathVariable Integer idViaje){
        return viajeService.verifyViaje(idViaje);
    }
}
