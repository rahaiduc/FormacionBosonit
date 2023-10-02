package com.block7crudvalidation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id_Asignatura;

    private String asignatura;

    @ManyToMany(mappedBy = "id_student")
    private List<Student> students;

    private String coments;

    @Column(nullable = false)
    private Date initial_date;

    private Date finish_date;
}
