package com.block5commandlinerunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class TerceraClase implements CommandLineRunner {


    @Override
    public void run( String... args) throws Exception {
        System.out.println("Soy la tercera clase");
    }
}
