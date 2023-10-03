package com.block7crudvalidation.domain;

import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentSimpleOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(generator = "custom-string-id-generator")
    @GenericGenerator(name = "custom-string-id-generator", strategy = "com.block7crudvalidation.domain.GeneradoresId.GeneradorIdPersona")
    private String id_student;

    @OneToOne
    @JoinColumn(name="id_persona", nullable = false, unique = true)
    private Persona persona;

    @Column(nullable = false)
    private int num_hours_week;

    private String coments;

    @ManyToOne
    @JoinColumn(name="id_profesor",nullable = false, unique = true)
    private Profesor profesor;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'FRONT'", nullable = false)
    private branchType branch;

    @ManyToMany
    @JoinTable(
            name = "asignatura_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "asignatura_id")
    )
    private List<Asignatura> asignaturas;


    public StudentOutputDto studentToStudentOutputDto(){
        return new StudentOutputDto(
                this.id_student,
                this.num_hours_week,
                this.coments,
                this.branch,
                this.persona.getId_persona(),
                this.persona.getUsuario(),
                this.persona.getPassword(),
                this.persona.getName(),
                this.persona.getSurname(),
                this.persona.getCompany_email(),
                this.persona.getPersonal_email(),
                this.persona.getCity(),
                this.persona.isActive(),
                this.persona.getCreated_date(),
                this.persona.getImagen_url(),
                this.persona.getTermination_date()
        );
    }

    public StudentSimpleOutputDto studentToStudentSimpleOutputDto(){
        String profesor_id = this.profesor != null ? this.profesor.getId_profesor() : null;
        return new StudentSimpleOutputDto(
                this.id_student,
                this.num_hours_week,
                this.coments,
                this.profesor,
                this.branch
        );
    }
}

