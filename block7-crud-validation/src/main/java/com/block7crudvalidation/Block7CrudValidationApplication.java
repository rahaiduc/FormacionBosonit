package com.block7crudvalidation;

import com.block7crudvalidation.controller.dto.inputs.AsignaturaInputDto;
import com.block7crudvalidation.controller.dto.inputs.ProfesorInputDto;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.domain.Mappers.AsignaturaMapper;
import com.block7crudvalidation.domain.Mappers.ProfesorMapper;
import com.block7crudvalidation.domain.Mappers.StudentMapper;
import com.block7crudvalidation.domain.Persona;
import com.block7crudvalidation.domain.Profesor;
import com.block7crudvalidation.domain.Student;
import com.block7crudvalidation.repository.AsignaturaRepository;
import com.block7crudvalidation.repository.PersonRepository;
import com.block7crudvalidation.repository.ProfesorRepository;
import com.block7crudvalidation.repository.StudentRepository;
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
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    @PostConstruct
    public void populateDb() {

        personRepository.save(new Persona("1","user1","pass1", "name1","Martinez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("2","user2","pass2", "name2", "Martin","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("3","user3","pass3", "name3", "Gonzalez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("4","user4","pass4", "name4","Fernandez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("5","user5","pass5", "name5","Gutierrez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("6","user6","pass6", "name6","San Martin","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("7","user7","pass7", "name7","Martinez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("8","user8","pass8", "name8", "Martin","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("9","user9","pass9", "name9", "Gonzalez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("10","user10","pass10", "name10","Fernandez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("11","user11","pass11", "name11","Gutierrez","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        personRepository.save(new Persona("12","user12","pass12", "name12","San Martin","abc@gmail.com","abc@gmail.com","Vallecas", new Date()));
        ProfesorInputDto profesorInputDto=new ProfesorInputDto("1","1","Profesor","Front");
        Profesor profesor= ProfesorMapper.INSTANCE.profesorInputDtoToProfesor(profesorInputDto);
        profesor.setPersona(personRepository.findById(profesorInputDto.getId_persona()).orElseThrow());
        profesorRepository.save(profesor);
        ProfesorInputDto profesorInputDto2=new ProfesorInputDto("2","3","Profesor","Front");
        Profesor profesor2= ProfesorMapper.INSTANCE.profesorInputDtoToProfesor(profesorInputDto2);
        profesor2.setPersona(personRepository.findById(profesorInputDto2.getId_persona()).orElseThrow());
        profesorRepository.save(profesor2);
        /*Poblamos la BDD con estudiantes*/
        StudentInputDto studentInputDto=new StudentInputDto("1","2","1",35,"Estudiante","Front");
        Student student= StudentMapper.INSTANCE.studentInputDtoToStudent(studentInputDto);
        student.setPersona(personRepository.findById(studentInputDto.getId_persona()).orElseThrow());
        student.setProfesor(profesorRepository.findById(studentInputDto.getId_profesor()).orElseThrow());
        studentRepository.save(student);

        /*Poblamos la BDD con asignaturas*/
        AsignaturaInputDto asignaturaInputDto=new AsignaturaInputDto("1","Sistemas Distribuidos","Asignatura",new Date(),new Date());
        AsignaturaInputDto asignaturaInputDto2=new AsignaturaInputDto("2","Angular","Asignatura",new Date(),new Date());
        asignaturaRepository.save(AsignaturaMapper.INSTANCE.asignaturaInputDtotoAsignatura(asignaturaInputDto));
        asignaturaRepository.save(AsignaturaMapper.INSTANCE.asignaturaInputDtotoAsignatura(asignaturaInputDto2));
    }

}
