package com.example.studentmanagementonlinesystem.service.impl;

import com.example.studentmanagementonlinesystem.entity.Student;
import com.example.studentmanagementonlinesystem.mapper.StudentMapper;
import com.example.studentmanagementonlinesystem.mapper.StudentCourseTeacherMapper;
import com.example.studentmanagementonlinesystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentCourseTeacherMapper studentCourseTeacherMapper;

    @Override
    public List<Student> findByPage(Integer num, Integer size) {
        // num is the zero-based page index; size is the number of records per page.
        List<Student> studentList = studentMapper.findAll();
        ArrayList<Student> list = new ArrayList<Student>();

        int start = size * num;
        int end = size * (num + 1);
        int sz = studentList.size();

        for (int i = start; i < end && i < sz; i++) {
            list.add(studentList.get(i));
        }

        return list;
    }

    @Override
    public Integer getLength() {
        return studentMapper.findAll().size();
    }

    @Override
    public Student findById(Integer sid) {
        return studentMapper.findById(sid);
    }

    @Override
    public boolean updateById(Student student) {
        return studentMapper.updateById(student);
    }

    @Override
    public Student register(Student student) {
        student.setSid(null);
        return studentMapper.register(student) ? student : null;
    }

    @Override
    @Transactional
    public boolean deleteById(Integer sid) {
        studentCourseTeacherMapper.deleteBySid(sid);
        return studentMapper.deleteById(sid);
    }
}
