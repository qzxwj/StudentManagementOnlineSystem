package com.example.studentmanagementonlinesystem.service;

import com.example.studentmanagementonlinesystem.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findByPage(Integer num, Integer size);

    List<Student> findBySearch(Integer sid, String sname, Integer fuzzy);

    Integer getLength();

    Student findById(Integer sid);

    boolean updateById(Student student);

    boolean save(Student student);

    boolean deleteById(Integer sid);
}
