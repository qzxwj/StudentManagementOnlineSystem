package com.example.studentmanagementonlinesystem.mapper;


import com.example.studentmanagementonlinesystem.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("SELECT sid, sname, password FROM s")
    List<Student> findAll();

    @Select("SELECT sid, sname, password FROM s WHERE sid = #{sid}")
    Student findById(@Param("sid") Integer sid);

    boolean updateById(@Param("student") Student student);

    @Insert("INSERT INTO s (sname, password) VALUES (#{student.sname}, #{student.password})")
    @Options(useGeneratedKeys = true, keyProperty = "student.sid", keyColumn = "sid")
    boolean register(@Param("student") Student student);

    @Delete("DELETE FROM s WHERE sid = #{sid}")
    boolean deleteById(@Param("sid") Integer sid);
}
