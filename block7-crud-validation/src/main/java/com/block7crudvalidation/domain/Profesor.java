package com.block7crudvalidation.domain;

import com.block7crudvalidation.controller.dto.outputs.ProfesorOutputDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="Profesor")
@Data
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
    private branchType branch;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Student> Students;

    public ProfesorOutputDto ProfesorToProfesorOutputDto(){
        return new ProfesorOutputDto(
                this.id_profesor,
                this.persona.getId_persona(),
                this.comments,
                this.branch
        );
    }
}
