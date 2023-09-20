package com.block6personcontrollers.configuracion;

import com.block6personcontrollers.modelos.Persona;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonaConfig {

    @Bean
    public Persona bean1(){
        return new Persona("bean1","pob1",1);
    }
    @Bean
    public Persona bean2(){
        return new Persona("bean2","pob2",2);
    }
    @Bean
    public Persona bean3(){
        return new Persona("bean3","pob3",3);
    }
}
