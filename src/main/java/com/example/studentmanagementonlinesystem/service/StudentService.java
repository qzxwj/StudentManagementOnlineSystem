package com.example.studentmanagementonlinesystem.service;

import com.example.studentmanagementonlinesystem.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findByPage(Integer num, Integer size);

    Integer getLength();

    Student findById(Integer sid);

    boolean updateById(Student student);

    Student register(Student student);

    boolean deleteById(Integer sid);
}
