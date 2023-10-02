package com.block7crudvalidation.application.interfaces;

import com.block7crudvalidation.controller.dto.inputs.StudentInputDto;
import com.block7crudvalidation.controller.dto.outputs.StudentOutputDto;

public interface StudentService {
    StudentOutputDto addStudent(StudentInputDto Student);
    StudentOutputDto getStudentById(int id);
    void deleteStudentById( int id);
    Iterable<StudentOutputDto> getAllStudents(int pageNumber, int pageSize);
    StudentOutputDto updateStudent(StudentInputDto Student);
}
