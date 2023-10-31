package com.block7crudvalidation;

import com.block7crudvalidation.domain.Persona;
import com.block7crudvalidation.repository.PersonRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Date;

@EnableFeignClients
@SpringBootApplication
public class Block7CrudValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Block7CrudValidationApplication.class, args);
    }

    @Autowired
    PersonRepository personRepository;

    @PostConstruct
    public void populateDb() {

        personRepository.save(new Persona("1","user1","pass1", "name1","Martinez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("2","user2","pass2", "name2", "Martin","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("3","user3","pass3", "name3", "Gonzalez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("4","user4","pass4", "name4","Fernandez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("5","user5","pass5", "name5","Gutierrez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("6","user6","pass6", "name6","San Martin","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));

    }

}
