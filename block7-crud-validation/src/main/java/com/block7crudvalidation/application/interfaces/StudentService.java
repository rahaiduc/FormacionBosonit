package com.block7crudvalidation.application.interfaces;

import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentFullOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentSimpleOutputDto;

import java.util.List;

public interface StudentService {
    StudentSimpleOutputDto addStudent(StudentInputDto Student);
    StudentFullOutputDto getFullStudentById(String id);
    StudentSimpleOutputDto getSimpleStudentById(String id);
    void deleteStudentById( String id);
    List<StudentSimpleOutputDto> getAllStudents();
    StudentSimpleOutputDto updateStudent(StudentInputDto Student);
}
