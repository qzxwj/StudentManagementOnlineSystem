package com.example.studentmanagementonlinesystem.service.impl;

import com.example.studentmanagementonlinesystem.entity.Student;
import com.example.studentmanagementonlinesystem.mapper.StudentMapper;
import com.example.studentmanagementonlinesystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

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
    public List<Student> findBySearch(Integer sid, String sname, Integer fuzzy) {
        Student student = new Student();
        student.setSid(sid);
        student.setSname(sname);
        fuzzy = (fuzzy == null) ? 0 : fuzzy;

        System.out.println();

        return studentMapper.findBySearch(student, fuzzy);
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
    public boolean save(Student student) {
        return studentMapper.save(student);
    }

    @Override
    public Student register(Student student) {
        student.setSid(null);
        return studentMapper.register(student) ? student : null;
    }

    @Override
    public boolean deleteById(Integer sid) {
        return studentMapper.deleteById(sid);
    }
}
