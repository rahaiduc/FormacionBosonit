package com.block7crudvalidation.application.impl;

import com.block7crudvalidation.application.interfaces.StudentService;
import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentOutputDto;

public class StudentServiceImpl implements StudentService {
    @Override
    public StudentOutputDto addStudent(StudentInputDto Student) {
        return null;
    }

    @Override
    public StudentOutputDto getStudentById(int id) {
        return null;
    }

    @Override
    public void deleteStudentById(int id) {

    }

    @Override
    public Iterable<StudentOutputDto> getAllStudents(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public StudentOutputDto updateStudent(StudentInputDto Student) {
        return null;
    }
}
