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
import java.util.Optional;

@Service
public class FicheroServiceImpl implements FicheroService, CommandLineRunner {
    private String ruta;
    private String directorio="..\\..\\FicherosSubidos";
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
        File path=new File(directorio);
        if(!path.isDirectory()){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"La ruta no es un directorio");
        }
        Path filePath = Paths.get(this.directorio).resolve(nombreFichero);
        Files.write(filePath, file.getBytes());

        Fichero fichero=new Fichero();
        fichero.setNombre(nombreFichero);
        fichero.setFechaSubida(new Date());
        fichero.setCategoria(tipo);
        return ficheroRepository.save(fichero).ficheroToFicheroOutputDto();
    }

    @Override
    public String modificarRuta(String ruta) {
        Path path = Paths.get(ruta);
        if(!Files.exists(path)){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"La ruta no es un directorio");
        }
        this.ruta = ruta;
        return "El path ha cambiado a ser: " + this.ruta;
    }

    @Override
    public FicheroOutputDto descargarFicheroId(int id) {
        Optional<Fichero> posiblefichero = ficheroRepository.findById(id);
        if(!posiblefichero.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"No se ha encontrado el fichero con el id "+id);
        }
        return descargarFichero(posiblefichero.get());
    }

    @Override
    public FicheroOutputDto descargarFicheroNombre(String nombre) {
        Optional<Fichero> posiblefichero = ficheroRepository.findByNombre(nombre);
        if(!posiblefichero.isPresent()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"No se ha encontrado el fichero con el nombre "+nombre);
        }
        return descargarFichero(posiblefichero.get());
    }
    private FicheroOutputDto descargarFichero(Fichero fichero){
        String rutaFichero = this.directorio + "/" + fichero.getNombre();

        Path path = Paths.get(rutaFichero);
        if (!Files.exists(path) || Files.isDirectory(path)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"No se ha encontrado el fichero en la ruta indicada.");
        }
        try {
            byte[] contenido = Files.readAllBytes(path);

            String nombreFichero = fichero.getNombre();
            Path filePath = Paths.get(this.ruta).resolve(nombreFichero);
            Files.write(filePath, contenido);

            return fichero.ficheroToFicheroOutputDto();
        } catch (IOException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Error al leer el contenido del fichero.");
        }
    }


}
