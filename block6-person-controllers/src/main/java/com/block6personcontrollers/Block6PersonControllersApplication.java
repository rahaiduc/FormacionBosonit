package com.block6personcontrollers;

import com.block6personcontrollers.modelos.Ciudad;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Block6PersonControllersApplication {

    public static void main(String[] args) {
        SpringApplication.run(Block6PersonControllersApplication.class, args);
    }


    @Bean
    public List<Ciudad> listaCiudades(){
        List<Ciudad> list=new ArrayList<>();
        list.add(new Ciudad("Vallecas",100000));
        list.add(new Ciudad("Cantabria",120500));
        list.add(new Ciudad("Barcelona",360050));
        return list;
    }

}
