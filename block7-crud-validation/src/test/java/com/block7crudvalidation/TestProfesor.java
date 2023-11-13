package com.block7crudvalidation;

import com.block7crudvalidation.controller.dto.inputs.AsignaturaInputDto;
import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.inputs.ProfesorInputDto;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonaEstudianteOutputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonaProfesorOutputDto;
import com.block7crudvalidation.controller.dto.outputs.ProfesorOutputDto;
import com.block7crudvalidation.domain.BranchType;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestProfesor {
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
    @DisplayName("Add profesor")
    public void addProfesorTest() throws Exception {
        //Insercion correcta
        ProfesorInputDto profesorInputDto=new ProfesorInputDto("3","4","Profesor","Front");
        String contenido=new ObjectMapper().writeValueAsString(profesorInputDto);
        this.mockMvc.perform(post("/profesor").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is2xxSuccessful()).andReturn();
        this.mockMvc.perform(get("/profesor/1")).andDo(print()).andExpect(status().isOk()).andReturn();


        //Insercion no correcta
        ProfesorInputDto profesorInputDto2=new ProfesorInputDto("4","25","Profesor","Front");
        contenido=new ObjectMapper().writeValueAsString(profesorInputDto2);
        try {
            this.mockMvc.perform(post("/profesor").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"404-Persona no existe");
        }
        ProfesorInputDto profesorInputDto3=new ProfesorInputDto("4","1","Profesor","Front");
        contenido=new ObjectMapper().writeValueAsString(profesorInputDto3);
        try {
            this.mockMvc.perform(post("/profesor").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is4xxClientError()).andReturn();
        }catch (HttpClientErrorException e){
            Assertions.assertEquals(e.getMessage(),"Esta persona ya tiene un profesor asignado");
        }
        ProfesorInputDto profesorInputDto4=new ProfesorInputDto("4","2","Profesor","Front");
        contenido=new ObjectMapper().writeValueAsString(profesorInputDto3);
        try {
            this.mockMvc.perform(post("/profesor").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is4xxClientError()).andReturn();
        }catch (HttpClientErrorException e){
            Assertions.assertEquals(e.getMessage(),"Esta persona es un estudiante");
        }
    }

    @Test
    @DisplayName("Get profesor")
    public void getProfesorTest() throws Exception {
        MvcResult result=this.mockMvc.perform(get("/profesor/1")).andDo(print()).andExpect(status().isOk()).andReturn();
        String contenido=result.getResponse().getContentAsString();
        ProfesorOutputDto profesorOutputDto=new ObjectMapper().readValue(contenido, new TypeReference<ProfesorOutputDto>() {   });
        Assertions.assertEquals("1",profesorOutputDto.getId_persona());

        //Obtener un profesor que no existe
        try{
            this.mockMvc.perform(get("/profesor/5")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"404 - No existe el profesor");
        }
    }
    @Test
    @DisplayName("Delete profesor")
    public void deleteProfesorTest() throws Exception {
        this.mockMvc.perform(delete("/profesor/1")).andDo(print()).andExpect(status().isOk()).andReturn();
        try{
            this.mockMvc.perform(get("/profesor/1")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"404 - No existe el profesor");
        }
        //Eliminacion de una persona que no existe
        try{
            this.mockMvc.perform(delete("/profesor/1")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"404 - No existe el profesor");
        }
    }

    @Test
    @DisplayName("Update profesor")
    public void updateProfesorTest() throws Exception {
        ProfesorInputDto profesorInputDto=new ProfesorInputDto(null,null,null,"Back");
        String contenido=new ObjectMapper().writeValueAsString(profesorInputDto);
        this.mockMvc.perform(put("/profesor/2").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is2xxSuccessful()).andReturn();
        MvcResult result=this.mockMvc.perform(get("/profesor/2")).andDo(print()).andExpect(status().isOk()).andReturn();
        String resultado=result.getResponse().getContentAsString();
        ProfesorOutputDto profesorOutputDto=new ObjectMapper().readValue(contenido, new TypeReference<ProfesorOutputDto>() {   });
        Assertions.assertEquals(BranchType.Back,profesorOutputDto.getBranch());
    }

}
