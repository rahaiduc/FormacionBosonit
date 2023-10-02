package com.block7crudvalidation.domain;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id_student;

    @OneToOne
    @JoinColumn(name="persona", nullable = false, unique = true)
    private Persona id_persona;

    @Column(nullable = false)
    private int num_hours_week;

    private String coments;

    @ManyToOne
    @JoinColumn(name="id_profesor",nullable = false, unique = true)
    private Profesor id_profesor;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'FRONT'", nullable = false)
    private branchType branch;

    @ManyToMany(mappedBy = "id_Asignatura")
    private List<Asignatura> asignaturas;


}
