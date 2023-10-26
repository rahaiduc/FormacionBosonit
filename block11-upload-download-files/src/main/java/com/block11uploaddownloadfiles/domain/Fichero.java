package com.block11uploaddownloadfiles.domain;

import com.block11uploaddownloadfiles.domain.dto.FicheroOutputDto;
import com.block11uploaddownloadfiles.repository.FicheroRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fichero {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private Date fechaSubida;
    @Column(nullable = false)
    private String categoria;

    public FicheroOutputDto ficheroToFicheroOutputDto(){
        return new FicheroOutputDto(
                this.id,
                this.nombre,
                this.fechaSubida,
                this.categoria
        );
    }

}
