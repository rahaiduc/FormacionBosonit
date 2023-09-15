package com.block5properties;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@PropertySource("classpath:application.yml")
public class BeanPropiedadesYML implements CommandLineRunner {

    @Value("${greeting1}")
    private String greeting;

    @Value("${my.number1}")
    private String Number;

    @Value("${NEW_PROPERTY1}")
    private String newProperty;

    @Value("${MYURL: No_existe}")
    private String myurl;

    @Value("${MYURL2:NO_tengo_valor}")
    private String myurl2;



    @Override
    public void run(String... args) throws Exception {
        System.out.println("-----------LEYENDO PROPIEDADES DESDE YAML--------------");
        System.out.println("El valor de greeting es: " + this.greeting);
        System.out.println("El valor de my.number es: " + this.Number);
        System.out.println("El valor de new.property es: " + this.newProperty);
        System.out.println("El valor de myurl es: " + this.myurl);
        System.out.println("El valor de myurl2 es: " + this.myurl2);
    }
}
