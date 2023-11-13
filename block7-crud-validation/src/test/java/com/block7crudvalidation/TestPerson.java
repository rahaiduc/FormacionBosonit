package com.block7crudvalidation;

import com.block7crudvalidation.controller.dto.inputs.AsignaturaInputDto;
import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.inputs.ProfesorInputDto;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonaEstudianteOutputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonaProfesorOutputDto;
import com.block7crudvalidation.domain.Mappers.AsignaturaMapper;
import com.block7crudvalidation.domain.Mappers.ProfesorMapper;
import com.block7crudvalidation.domain.Mappers.StudentMapper;
import com.block7crudvalidation.domain.Profesor;
import com.block7crudvalidation.domain.Student;
import com.block7crudvalidation.repository.AsignaturaRepository;
import com.block7crudvalidation.repository.PersonRepository;
import com.block7crudvalidation.repository.ProfesorRepository;
import com.block7crudvalidation.repository.StudentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestPerson {
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

    }

    @Test
    @DisplayName("Add persona")
    public void addPersonTest() throws Exception {
        //Insercion correcta
        PersonInputDto personInputDto=new PersonInputDto("13","usuario4","pass4","Jesus","Maria","abc@gmail.com","abc@gmail.com","vallecas",false,new Date(),null,null);
        String contenido=new ObjectMapper().writeValueAsString(personInputDto);
        this.mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is2xxSuccessful()).andReturn();
        this.mockMvc.perform(get("/person/13")).andDo(print()).andExpect(status().isOk()).andReturn();


        //Insercion no correcta
        PersonInputDto personInputDto2=new PersonInputDto("13",null,"pass4","Jesus","Maria","abc@gmail.com","abc@gmail.com","vallecas",false,new Date(),null,null);
        contenido=new ObjectMapper().writeValueAsString(personInputDto2);
        try {
            this.mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is4xxClientError()).andReturn();
        }catch (HttpClientErrorException e){
            Assertions.assertEquals(e.getMessage(),"Algun/os valores no pueden ser nulos");
        }
        PersonInputDto personInputDto3=new PersonInputDto("14","usuariousuariousuario","pass4","Jesus","Maria","abc@gmail.com","abc@gmail.com","vallecas",false,new Date(),null,null);
        contenido=new ObjectMapper().writeValueAsString(personInputDto3);
        try {
            this.mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is4xxClientError()).andReturn();
        }catch (HttpClientErrorException e){
            Assertions.assertEquals(e.getMessage(),"El usuario tiene que tener entre 6 y 10 caracteres");
        }
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

        //Obtener una persona que no existe
        try{
            this.mockMvc.perform(get("/person/20")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"404-Persona no encontrada");
        }
    }
    @Test
    @DisplayName("Delete persona")
    public void deletePersonTest() throws Exception {
        this.mockMvc.perform(delete("/person/3")).andDo(print()).andExpect(status().isOk()).andReturn();
        try{
            this.mockMvc.perform(get("/person/3")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"404-Persona no encontrada");
        }
        //Eliminacion de una persona que no existe
        try{
            this.mockMvc.perform(delete("/person/3")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"404-Persona no encontrada");
        }
    }

    @Test
    @DisplayName("Update persona")
    public void updatePersonTest() throws Exception {
        PersonInputDto personInputDto=new PersonInputDto(null,"userchange",null,null,null,null,null,null,false,null,null,null);
        String contenido=new ObjectMapper().writeValueAsString(personInputDto);
        this.mockMvc.perform(put("/person/10").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is2xxSuccessful()).andReturn();
        MvcResult result=this.mockMvc.perform(get("/person/10")).andDo(print()).andExpect(status().isOk()).andReturn();
        String resultado=result.getResponse().getContentAsString();
        PersonOutputDto personOutputDto;
        if(contenido.contains("profesorOutputDto")) {
            personOutputDto= new ObjectMapper().readValue(resultado, new TypeReference<PersonaProfesorOutputDto>() {   });
        }else if(contenido.contains("studentFullOutputDto")){
            personOutputDto= new ObjectMapper().readValue(resultado, new TypeReference<PersonaEstudianteOutputDto>() {   });
        }else{
            personOutputDto= new ObjectMapper().readValue(resultado, new TypeReference<>() {});
        }
        Assertions.assertEquals("userchange",personOutputDto.getUsuario());
    }


}
