package com.block7crudvalidation.domain;

import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_persona")
    private String id_persona;
    private String usuario;
    private String password;
    private String name;
    private String surname;
    private String company_email;
    private String personal_email;
    private String city;
    private boolean active;
    private Date created_date;
    private String imagen_url;
    private Date termination_date;


    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private Student student;
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profesor profesor;
    @jakarta.persistence.Id
    private String id;



    public Persona(PersonInputDto personInputDto) {
        this.id_persona = personInputDto.getId_persona();
        this.name = personInputDto.getName();
        this.surname= personInputDto.getSurname();
        this.company_email =personInputDto.getCompany_email();
        this.usuario=personInputDto.getUsuario();
        this.password=personInputDto.getPassword();
        this.personal_email=personInputDto.getPersonal_email();
        this.active=personInputDto.isActive();
        this.city=personInputDto.getCity();
        this.created_date=personInputDto.getCreated_date();
        this.imagen_url=personInputDto.getImagen_url();
        this.termination_date=personInputDto.getTermination_date();
    }

    public PersonOutputDto personToPersonOutputDto() {
        return new PersonOutputDto(
                this.id_persona,
                this.usuario,
                this.name,
                this.surname,
                this.company_email,
                this.active
        );
    }

    public void setId(String id) {
        this.id = id;
    }

}

