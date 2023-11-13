package com.block7crudvalidation;

import com.block7crudvalidation.controller.dto.inputs.AsignaturaInputDto;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.AsignaturaOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentSimpleOutputDto;
import com.block7crudvalidation.domain.BranchType;
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
public class TestAsignatura {
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
    @DisplayName("Add Asignatura")
    public void addAsignaturaTest() throws Exception {
        //Insercion correcta
        AsignaturaInputDto asignaturaInputDto=new AsignaturaInputDto("3","Angular","Asignatura",new Date(),new Date());
        String contenido=new ObjectMapper().writeValueAsString(asignaturaInputDto);
        this.mockMvc.perform(post("/asignatura").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is2xxSuccessful()).andReturn();
        this.mockMvc.perform(get("/asignatura/3")).andDo(print()).andExpect(status().isOk()).andReturn();


        //Insercion no correcta
        AsignaturaInputDto asignaturaInputDto2=new AsignaturaInputDto("4",null,"Asignatura",new Date(),new Date());
        contenido=new ObjectMapper().writeValueAsString(asignaturaInputDto2);
        try {
            this.mockMvc.perform(post("/asignatura").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is4xxClientError()).andReturn();
        }catch (HttpClientErrorException e){
            Assertions.assertEquals(e.getMessage(),"Algun/os valores no pueden ser nulos");
        }

    }

    @Test
    @DisplayName("Get Asignatura")
    public void getAsignaturaTest() throws Exception {
        MvcResult result=this.mockMvc.perform(get("/asignatura/2")).andDo(print()).andExpect(status().isOk()).andReturn();
        String contenido=result.getResponse().getContentAsString();
        AsignaturaOutputDto asignaturaOutputDto=new ObjectMapper().readValue(contenido, new TypeReference<AsignaturaOutputDto>() {   });
        Assertions.assertEquals("2",asignaturaOutputDto.getId_asignatura());

        //Obtener una asignatura que no existe
        try{
            this.mockMvc.perform(get("/asignatura/5")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"404 - Asignatura no encontrada");
        }
    }
    @Test
    @DisplayName("Delete Asignatura")
    public void deleteAsignaturaTest() throws Exception {
        this.mockMvc.perform(delete("/asignatura/1")).andDo(print()).andExpect(status().isOk()).andReturn();
        try{
            this.mockMvc.perform(get("/asignatura/1")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"404 - Asignatura no encontrada");
        }
        //Eliminacion de una asignatura que no existe
        try{
            this.mockMvc.perform(delete("/asignatura/1")).andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        }catch (NoSuchElementException e){
            Assertions.assertEquals(e.getMessage(),"404 - Asignatura no encontrada");
        }
    }

    @Test
    @DisplayName("Update Asignatura")
    public void updateAsignatura() throws Exception {
        AsignaturaInputDto asignaturaInputDto=new AsignaturaInputDto(null,"Spring",null,null,null);
        String contenido=new ObjectMapper().writeValueAsString(asignaturaInputDto);
        this.mockMvc.perform(put("/asignatura/2").contentType(MediaType.APPLICATION_JSON).content(contenido)).andExpect(status().is2xxSuccessful()).andReturn();
        MvcResult result=this.mockMvc.perform(get("/asignatura/2")).andDo(print()).andExpect(status().isOk()).andReturn();
        String resultado=result.getResponse().getContentAsString();
        AsignaturaOutputDto asignaturaOutputDto=new ObjectMapper().readValue(resultado, new TypeReference<AsignaturaOutputDto>() {   });
        Assertions.assertEquals("Spring",asignaturaOutputDto.getAsignatura());
    }
}
