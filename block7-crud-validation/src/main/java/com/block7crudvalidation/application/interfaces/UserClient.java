package com.block7crudvalidation.application.interfaces;

import com.block7crudvalidation.controller.dto.outputs.ProfesorOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="http://localhost:8081",name = "profesor-service")
public interface UserClient {
    @GetMapping("/profesor/{id}")
    ProfesorOutputDto getProfesor(@PathVariable int id);

}
