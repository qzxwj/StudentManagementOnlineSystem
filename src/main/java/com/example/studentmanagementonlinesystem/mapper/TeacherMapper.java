package com.example.studentmanagementonlinesystem.mapper;


import com.example.studentmanagementonlinesystem.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("SELECT tid, tname, password FROM t")
    List<Teacher> findAll();

    @Select("SELECT tid, tname, password FROM t WHERE tid = #{tid}")
    Teacher findById(@Param("tid") Integer tid);

    List<Teacher> findBySearch(@Param("tid") Integer tid, @Param("tname") String tname, @Param("fuzzy") Integer fuzzy);

    boolean updateById(@Param("teacher") Teacher teacher);

    @Insert("INSERT INTO t (tname, password) VALUES (#{teacher.tname}, #{teacher.password})")
    @Options(useGeneratedKeys = true, keyProperty = "teacher.tid", keyColumn = "tid")
    boolean register(@Param("teacher") Teacher teacher);

    @Delete("DELETE FROM t WHERE tid = #{tid}")
    boolean deleteById(@Param("tid") Integer tid);
}
