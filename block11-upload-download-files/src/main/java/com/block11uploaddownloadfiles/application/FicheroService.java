package com.block11uploaddownloadfiles.application;

import com.block11uploaddownloadfiles.domain.dto.FicheroOutputDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FicheroService {
    FicheroOutputDto subirFichero(String tipo, MultipartFile file) throws IOException;
    String modificarRuta(String ruta);
    String descargarFicheroId(int id);
    String descargarFicheroNombre(String nombre);
}
