package com.block7crudvalidation;

import com.block7crudvalidation.controller.dto.inputs.AsignaturaInputDto;
import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.inputs.ProfesorInputDto;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonaEstudianteOutputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonaProfesorOutputDto;
import com.block7crudvalidation.controller.dto.outputs.ProfesorOutputDto;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCRUD {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AsignaturaRepository asignaturaRepository;


    /**
     * Data loading into embedded MongoDB.
     * When we run this test the application is started again, so we have the database empty although in the previous test we have loaded data.
     */
    @BeforeAll
    public  void starting()
    {
        /*Ya hemos poblado al BDD con personas, cuando se lanza la aplicacion Spring Boot con la anotacion PostConstruct*/
        /*Poblamos la BDD con profesores*/
        ProfesorInputDto profesorInputDto=new ProfesorInputDto("1","1","Profesor","Back");
        Profesor profesor= ProfesorMapper.INSTANCE.profesorInputDtoToProfesor(profesorInputDto);
        profesor.setPersona(personRepository.findById(profesorInputDto.getId_persona()).orElseThrow());
        profesorRepository.save(profesor);

        /*Poblamos la BDD con estudiantes*/
        StudentInputDto studentInputDto=new StudentInputDto("1","2","1",35,"Estudiante","Back");
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

    @Test
    @DisplayName("Add persona")
    public void addPersonTest() throws Exception {
        PersonInputDto personInputDto=new PersonInputDto("13","usuario4","pass4","Jesus","Maria","abc@gmail.com","abc@gmail.com","vallecas",false,new Date(),null,null);
        String contenido=new ObjectMapper().writeValueAsString(personInputDto);
        this.mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is2xxSuccessful()).andReturn();
        Assertions.assertEquals(13,personRepository.findAll().size());
    }

    @Test
    @DisplayName("Get persona")
    public void getPersonTest() throws Exception {
        MvcResult result=this.mockMvc.perform(get("/person/2")).andDo(print()).andExpect(status().isOk()).andReturn();
        String contenido=result.getResponse().getContentAsString();
        PersonOutputDto personOutputDto;
        if(contenido.contains("profesorOutputDto")) {
            personOutputDto= new ObjectMapper().readValue(contenido, new TypeReference<PersonaProfesorOutputDto>() {   });
        }else if(contenido.contains("studentFullOutputDto")){
            personOutputDto= new ObjectMapper().readValue(contenido, new TypeReference<PersonaEstudianteOutputDto>() {   });
        }else{
            personOutputDto= new ObjectMapper().readValue(contenido, new TypeReference<>() {});
        }
        Assertions.assertEquals("user2",personOutputDto.getUsuario());

    }
    /*@Test
    @DisplayName("Calling getAll with mockMVC")
    void mockController() throws Exception {
        MvcResult resultado  = this.mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn();
        String contenido= resultado.getResponse().getContentAsString();

        List<Customer> customers= new ObjectMapper().readValue(contenido, new TypeReference<List<Customer>>() {   }); // Use TypeReference to map the List.

        Assertions.assertEquals(customers.size(), 1);
        Assertions.assertEquals(customers.get(0).getFirstName(),DemoSpringTestApplicationTests.FIRSTNAME);
    }*/
}
