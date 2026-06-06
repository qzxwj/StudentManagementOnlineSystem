package com.example.studentmanagementonlinesystem.mapper;


import com.example.studentmanagementonlinesystem.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<Student> findAll();

    Student findById(@Param("sid") Integer sid);

    boolean updateById(@Param("student") Student student);

    boolean register(@Param("student") Student student);

    boolean deleteById(@Param("sid") Integer sid);
}
