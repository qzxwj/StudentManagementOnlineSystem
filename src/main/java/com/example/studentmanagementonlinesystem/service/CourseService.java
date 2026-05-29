package com.example.studentmanagementonlinesystem.service;

import com.example.studentmanagementonlinesystem.entity.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {
    List<Course> findBySearch(Map<String, String> map);

    List<Course> findBySearch(Integer cid);

    List<Course> findById(Integer cid);

    boolean updateById(Course course);

    boolean insertCourse(Course course);

    boolean deleteById(Integer cid);
}
