package com.block7crudvalidation.domain;

import com.block7crudvalidation.controller.dto.outputs.ProfesorOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentSimpleOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentsAsignaturas;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="Profesor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {
    @Id
    @GeneratedValue(generator = "custom-string-id-generator")
    @GenericGenerator(name = "custom-string-id-generator", strategy = "com.block7crudvalidation.domain.GeneradoresId.GeneradorIdPersona")
    private String id_profesor;

    @OneToOne
    @JoinColumn(name = "id_persona",nullable = false,unique = true)
    private Persona persona;

    private String comments;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'FRONT'", nullable = false)
    private BranchType branch;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Student> students=new HashSet<>();

    public ProfesorOutputDto ProfesorToProfesorOutputDto(){
        return new ProfesorOutputDto(
                this.id_profesor,
                this.persona.getId_persona(),
                this.comments,
                this.branch,
                /*this.students==null? new HashSet<StudentsAsignaturas>() :*/this.students.stream().map(Student::studentToStudentAsignaturas).collect(Collectors.toSet())
        );
    }
}
