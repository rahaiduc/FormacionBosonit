package com.block11uploaddownloadfiles.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FicheroInputDto {
    private int id;
    private String nombre;
    private Date fechaSubida;
    private String categoria;
}
