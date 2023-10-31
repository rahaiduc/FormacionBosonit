package com.block7crudvalidation.repository;

import com.block7crudvalidation.controller.dto.outputs.PersonOutputDto;

import java.util.HashMap;
import java.util.List;

public interface PersonRepositoryCustom {
    public List<PersonOutputDto> getGreaterQuery(
            HashMap<String, Object> conditions);
    public List<PersonOutputDto> getLessQuery(
            HashMap<String, Object> conditions);
}
