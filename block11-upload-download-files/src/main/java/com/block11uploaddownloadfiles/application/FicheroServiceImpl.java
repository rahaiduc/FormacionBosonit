package com.block11uploaddownloadfiles.application;

import com.block11uploaddownloadfiles.domain.Fichero;
import com.block11uploaddownloadfiles.domain.dto.FicheroOutputDto;
import com.block11uploaddownloadfiles.repository.FicheroRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FicheroServiceImpl implements FicheroService, CommandLineRunner {
    private String ruta;
    @Autowired
    FicheroRepository ficheroRepository;

    @Override
    public void run(String... args) throws Exception {
        this.ruta=args.length>0 ? args[0] : System.getProperty("user.dir");
    }
    @Override
    public FicheroOutputDto subirFichero(String tipo, MultipartFile file) throws IOException {
        String nombreFichero = file.getOriginalFilename();
        if (!nombreFichero.endsWith(tipo)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"El fichero debe tener extensión " + tipo);
        }
        if(this.ruta==null){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"La ruta esta vacia");
        }
        File directorio=new File(this.ruta);
        if(!directorio.isDirectory()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"La ruta no es un directorio");
        }
        Path filePath = Paths.get(this.ruta).resolve(nombreFichero);
        Files.write(filePath, file.getBytes());

        Fichero fichero=new Fichero();
        fichero.setNombre(nombreFichero);
        fichero.setFechaSubida(new Date());
        fichero.setCategoria(tipo);
        return ficheroRepository.save(fichero).ficheroToFicheroOutputDto();
    }

    @Override
    public String modificarRuta(String ruta) {
        return null;
    }

    @Override
    public String descargarFicheroId(int id) {
        return null;
    }

    @Override
    public String descargarFicheroNombre(String nombre) {
        return null;
    }

}
