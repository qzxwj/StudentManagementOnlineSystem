package com.example.studentmanagementonlinesystem.service;

import com.example.studentmanagementonlinesystem.entity.Teacher;

import java.util.List;
import java.util.Map;

public interface TeacherService {
    List<Teacher> findBySearch(Map<String, String> map);

    Teacher findById(Integer tid);

    boolean updateById(Teacher teacher);

    boolean save(Teacher teacher);

    boolean deleteById(Integer tid);
}
