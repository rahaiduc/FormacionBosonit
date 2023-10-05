package com.block7crudvalidation.domain;

import com.block7crudvalidation.controller.dto.inputs.PersonInputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonaEstudianteOutputDto;
import com.block7crudvalidation.controller.dto.outputs.PersonaProfesorOutputDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Entity
@Table(name="Persona")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    @jakarta.persistence.Id
    @Id
    @Column(name = "id_persona")
    @GeneratedValue(generator = "custom-string-id-generator")
    @GenericGenerator(name = "custom-string-id-generator", strategy = "com.block7crudvalidation.domain.GeneradoresId.GeneradorIdPersona")
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

    public PersonaProfesorOutputDto personToPersonProfesorOutputDto() {
        return new PersonaProfesorOutputDto(
                this.id_persona,
                this.usuario,
                this.name,
                this.surname,
                this.company_email,
                this.active,
                this.profesor.ProfesorToProfesorOutputDto()
        );
    }

    public PersonaEstudianteOutputDto personToPersonaEstudianteOutputDto() {
        return new PersonaEstudianteOutputDto(
                this.id_persona,
                this.usuario,
                this.name,
                this.surname,
                this.company_email,
                this.active,
                this.student.studentToStudentFulltOutputDto()
        );
    }


    public void setId(String id) {
        this.id_persona = id;
    }



}

