package com.block7crud.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchElementFoundException extends RuntimeException {
    String message;

    public NoSuchElementFoundException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}