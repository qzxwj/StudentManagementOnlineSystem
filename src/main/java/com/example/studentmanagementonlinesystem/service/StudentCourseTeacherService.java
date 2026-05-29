package com.example.studentmanagementonlinesystem.service;

import com.example.studentmanagementonlinesystem.entity.CourseTeacherInfo;
import com.example.studentmanagementonlinesystem.entity.StudentCourseTeacher;
import com.example.studentmanagementonlinesystem.entity.StudentCourseTeacherInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface StudentCourseTeacherService {
    List<CourseTeacherInfo> findBySid(Integer sid, String term);

    List<String> findAllTerm();

    boolean isSCTExist(StudentCourseTeacher studentCourseTeacher);

    boolean save(StudentCourseTeacher studentCourseTeacher);

    boolean deleteBySCT(StudentCourseTeacher studentCourseTeacher);

    boolean deleteById(Integer sid, Integer cid, Integer tid, String term);

    StudentCourseTeacherInfo findByIdWithTerm(Integer sid, Integer cid, Integer tid, String term);

    boolean updateById(Integer sid, Integer cid, Integer tid, String term, Integer grade);

    List<StudentCourseTeacherInfo> findBySearch(Map<String, String> map);
}
