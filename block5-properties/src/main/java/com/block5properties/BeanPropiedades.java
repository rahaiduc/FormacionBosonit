package com.block5properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
@Component
public class BeanPropiedades implements CommandLineRunner{

    @Value("${greeting}")
    private String greeting;

    @Value("${my.number}")
    private String Number;

    @Value("${NEW_PROPERTY}")
    private String newProperty;

    /*@Value("${MYURL}")
    private String myurl;*/

    @Value("${MYURL2:NO_tengo_valor}")
    private String myurl2;
    BeanPropiedades(){

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("El valor de greeting es: " + this.greeting);
        System.out.println("El valor de my.number es: " + this.Number);
        System.out.println("El valor de new.property es: " + this.newProperty);
        //System.out.println("El valor de myurl2 es: " + this.myurl);
        System.out.println("El valor de myurl2 es: " + this.myurl2);
    }
}
