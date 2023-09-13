package com.block5commandlinerunner;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class ClaseInicial {

    @PostConstruct
    public void ejecutar() {
        System.out.println("Hola desde clase inicial");
    }
}
