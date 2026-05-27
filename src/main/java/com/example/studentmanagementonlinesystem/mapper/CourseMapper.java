package com.example.studentmanagementonlinesystem.mapper;

import com.example.studentmanagementonlinesystem.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> findBySearch(@Param("cid") Integer cid,
                              @Param("cname") String cname, @Param("fuzzy") Integer fuzzy,
                              @Param("lowBound") Integer lowBound, @Param("highBound") Integer highBound);

    boolean insertCourse(@Param("course") Course course);

    boolean updateById(@Param("course") Course course);

    boolean deleteById(@Param("cid") Integer cid);
}
