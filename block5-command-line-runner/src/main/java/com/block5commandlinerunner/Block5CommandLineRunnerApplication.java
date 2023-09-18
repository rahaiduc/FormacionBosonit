package com.block5commandlinerunner;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class Block5CommandLineRunnerApplication  {

    public static void main(String[] args) {
        SpringApplication.run(Block5CommandLineRunnerApplication.class, args);
    }

    @Bean
    CommandLineRunner ejecutame()
    {
        return p ->
        {
            System.out.println("Soy la tercera clase");
        };
    }





}

