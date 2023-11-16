package com.block7crudvalidation.controller.dto.outputs;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginOutputDto {
    private String token;
    private long expiresIn;
}
