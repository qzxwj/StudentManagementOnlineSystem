package com.example.studentmanagementonlinesystem.service.impl;

import com.example.studentmanagementonlinesystem.entity.Teacher;
import com.example.studentmanagementonlinesystem.mapper.CourseTeacherMapper;
import com.example.studentmanagementonlinesystem.mapper.StudentCourseTeacherMapper;
import com.example.studentmanagementonlinesystem.mapper.TeacherMapper;
import com.example.studentmanagementonlinesystem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentCourseTeacherMapper studentCourseTeacherMapper;

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Override
    public List<Teacher> findBySearch(Map<String, String> map) {
        Integer tid = null;
        String tname = null;
        Integer fuzzy = null;
        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("tname")) {
            tname = map.get("tname");
        }
        if (map.containsKey("fuzzy")) {
            fuzzy = map.get("fuzzy").equals("true") ? 1 : 0;
        }
        System.out.println("Teacher search map: " + map);
        System.out.println("Teacher search params: tid=" + tid + ", tname=" + tname + ", fuzzy=" + fuzzy);
        return teacherMapper.findBySearch(tid, tname, fuzzy);
    }

    @Override
    public Teacher findById(Integer tid) {
        return teacherMapper.findById(tid);
    }

    @Override
    public boolean updateById(Teacher teacher) {
        return teacherMapper.updateById(teacher);
    }

    @Override
    public boolean save(Teacher teacher) {
        return teacherMapper.save(teacher);
    }

    @Override
    public Teacher register(Teacher teacher) {
        teacher.setTid(null);
        return teacherMapper.register(teacher) ? teacher : null;
    }

    @Override
    @Transactional
    public boolean deleteById(Integer tid) {
        Teacher teacher = teacherMapper.findById(tid);
        if (teacher == null || "admin".equals(teacher.getTname())) {
            return false;
        }
        studentCourseTeacherMapper.deleteByTid(tid);
        courseTeacherMapper.deleteByTid(tid);
        return teacherMapper.deleteById(tid);
    }
}
