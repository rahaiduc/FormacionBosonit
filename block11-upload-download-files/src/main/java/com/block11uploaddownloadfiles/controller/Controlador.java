package com.block11uploaddownloadfiles.controller;

import com.block11uploaddownloadfiles.application.FicheroService;
import com.block11uploaddownloadfiles.domain.CustomError;
import com.block11uploaddownloadfiles.domain.dto.FicheroOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;

@RestController
public class Controlador {

    @Autowired
    FicheroService ficheroService;

    @PostMapping("/upload/{tipo}")
    public FicheroOutputDto subirFichero(@PathVariable String tipo,
                                         @RequestParam("file") MultipartFile file) throws IOException {
        return ficheroService.subirFichero(tipo,file);
    }

    @GetMapping("/setpath")
    public String cambiarPath(@RequestParam("path") String ruta){
        return ficheroService.modificarRuta(ruta);
    }

    @GetMapping("/{id}")
    public String devolverFicheroId(@PathVariable int id){
        return ficheroService.descargarFicheroId(id);
    }

    @GetMapping("/nombre/{nombre}")
    public String devolverFicheroNombre(@PathVariable String nombre){
        return ficheroService.descargarFicheroNombre(nombre);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<CustomError> handleUnprocessableEntity(HttpClientErrorException hee) {
        CustomError ce = new CustomError();
        ce.setTimestamp(new Date());
        ce.setHttpCode(HttpStatus.BAD_REQUEST.value());
        ce.setMensaje(hee.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ce);
    }
}
