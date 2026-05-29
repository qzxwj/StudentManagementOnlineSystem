package com.example.studentmanagementonlinesystem.service;


import com.example.studentmanagementonlinesystem.entity.Course;
import com.example.studentmanagementonlinesystem.entity.CourseTeacher;
import com.example.studentmanagementonlinesystem.entity.CourseTeacherInfo;

import java.util.List;
import java.util.Map;

public interface CourseTeacherService {
    boolean insertCourseTeacher(Integer cid, Integer tid, String term);

    List<Course> findMyCourse(Integer tid, String term);

    List<CourseTeacherInfo> findCourseTeacherInfo(Map<String, String> map);

    List<CourseTeacher> findBySearch(Integer cid, Integer tid, String term);

    List<CourseTeacher> findBySearch(Map<String, String> map);

    boolean deleteById(CourseTeacher courseTeacher);
}
