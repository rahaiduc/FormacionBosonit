package com.block7crudvalidation;

import com.block7crudvalidation.controller.dto.inputs.ProfesorInputDto;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.ProfesorOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentSimpleOutputDto;
import com.block7crudvalidation.domain.BranchType;
import com.block7crudvalidation.domain.Mappers.StudentMapper;
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

import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestEstudiante {
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
    @DisplayName("Add Estudiante")
    public void addEstudianteTest() throws Exception {
        //Insercion correcta
        StudentInputDto studentInputDto=new StudentInputDto("4","7","1",35,"Estudiante","Front");
        String contenido=new ObjectMapper().writeValueAsString(studentInputDto);
        this.mockMvc.perform(post("/estudiante").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is2xxSuccessful()).andReturn();
        this.mockMvc.perform(get("/estudiante/2")).andDo(print()).andExpect(status().isOk()).andReturn();


        //Insercion no correcta
        StudentInputDto studentInputDto2=new StudentInputDto("5","25","3",35,"Estudiante","Front");
        contenido=new ObjectMapper().writeValueAsString(studentInputDto2);
        try {
            this.mockMvc.perform(post("/estudiante").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"404-Persona no existe");
        }
        StudentInputDto studentInputDto3=new StudentInputDto("5","1","2",35,"Estudiante","Front");
        contenido=new ObjectMapper().writeValueAsString(studentInputDto3);
        try {
            this.mockMvc.perform(post("/estudiante").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is4xxClientError()).andReturn();
        }catch (HttpClientErrorException e){
            Assertions.assertEquals(e.getMessage(),"Esta persona es un profesor");
        }
        StudentInputDto studentInputDto4=new StudentInputDto("5","3","2",35,"Estudiante","Front");
        contenido=new ObjectMapper().writeValueAsString(studentInputDto4);
        try {
            this.mockMvc.perform(post("/estudiante").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is4xxClientError()).andReturn();
        }catch (HttpClientErrorException e){
            Assertions.assertEquals(e.getMessage(),"Esta persona ya tiene un estudiante asignado");
        }
    }

    @Test
    @DisplayName("Get Estudiante")
    public void getEstudianteTest() throws Exception {
        MvcResult result=this.mockMvc.perform(get("/estudiante/2")).andDo(print()).andExpect(status().isOk()).andReturn();
        String contenido=result.getResponse().getContentAsString();
        StudentSimpleOutputDto studentSimpleOutputDto=new ObjectMapper().readValue(contenido, new TypeReference<StudentSimpleOutputDto>() {   });
        Assertions.assertEquals("2",studentSimpleOutputDto.getId_student());

        //Obtener un profesor que no existe
        try{
            this.mockMvc.perform(get("/estudiante/5")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"id no encontrado del estudiante 5");
        }
    }
    @Test
    @DisplayName("Delete Estudiante")
    public void deleteEstudianteTest() throws Exception {
        this.mockMvc.perform(delete("/estudiante/3")).andDo(print()).andExpect(status().isOk()).andReturn();
        try{
            this.mockMvc.perform(get("/estudiante/3")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"id no encontrado del estudiante 3");
        }
        //Eliminacion de una persona que no existe
        try{
            this.mockMvc.perform(delete("/estudiante/3")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"id no encontrado del estudiante 3");
        }
    }

    @Test
    @DisplayName("Update Estudiante")
    public void updateEstudiante() throws Exception {
        StudentInputDto studentInputDto=new StudentInputDto(null,null,null,0,null,"Back");
        String contenido=new ObjectMapper().writeValueAsString(studentInputDto);
        this.mockMvc.perform(put("/estudiante/2").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is2xxSuccessful()).andReturn();
        MvcResult result=this.mockMvc.perform(get("/estudiante/2")).andDo(print()).andExpect(status().isOk()).andReturn();
        String resultado=result.getResponse().getContentAsString();
        StudentSimpleOutputDto studentSimpleOutputDto=new ObjectMapper().readValue(resultado, new TypeReference<StudentSimpleOutputDto>() {   });
        Assertions.assertEquals(BranchType.Back,studentSimpleOutputDto.getBranch());
    }

}
