package com.block7crudvalidation.application.interfaces;

import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentOutputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentSimpleOutputDto;

import java.util.List;

public interface StudentService {
    StudentOutputDto addStudent(StudentInputDto Student);
    StudentOutputDto getFullStudentById(String id);
    StudentSimpleOutputDto getSimpleStudentById(String id);
    void deleteStudentById( String id);
    List<StudentOutputDto> getAllStudents();
    StudentOutputDto updateStudent(StudentInputDto Student);
}
