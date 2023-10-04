package com.block7crudvalidation.domain;

import com.block7crudvalidation.controller.dto.outputs.AsignaturaOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Asignatura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asignatura {
    @Id
    @GeneratedValue(generator = "custom-string-id-generator")
    @GenericGenerator(name = "custom-string-id-generator", strategy = "com.block7crudvalidation.domain.GeneradoresId.GeneradorIdPersona")
    private String id_Asignatura;

    private String asignatura;

    @ManyToMany
    @JoinTable(
            name = "asignatura_student",
            joinColumns = @JoinColumn(name = "asignatura_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;

    private String coments;

    @Column(nullable = false)
    private Date initial_date;

    private Date finish_date;

    public AsignaturaOutputDto AsignaturaToAsignaturaOutputDto(){
        return new AsignaturaOutputDto(
            this.id_Asignatura,
            this.asignatura,
            this.coments,
            this.initial_date,
            this.finish_date
        );
    }
}
