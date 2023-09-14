package com.block5properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
@Component
public class BeanPropiedades implements CommandLineRunner{

    @Value("${greeting}")
    private String greeting;

    @Value("${my.number}")
    private String Number;
    private String newProperty;

    BeanPropiedades(@Value("${new.property:new property no tiene valor}") String newProperty){
        this.newProperty=newProperty;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("El valor de greeting es: " + this.greeting);
        System.out.println("El valor de my.number es: " + this.Number);
        System.out.println("El valor de new.property es: " + this.newProperty);
    }
}
