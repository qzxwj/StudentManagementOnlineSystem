package com.example.studentmanagementonlinesystem.service.impl;

import com.example.studentmanagementonlinesystem.entity.Course;
import com.example.studentmanagementonlinesystem.entity.CourseTeacher;
import com.example.studentmanagementonlinesystem.entity.CourseTeacherInfo;
import com.example.studentmanagementonlinesystem.mapper.CourseTeacherMapper;
import com.example.studentmanagementonlinesystem.service.CourseTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {
    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Override
    public boolean insertCourseTeacher(Integer cid, Integer tid, String term) {
        return courseTeacherMapper.insertCourseTeacher(cid, tid, term);
    }

    @Override
    public List<Course> findMyCourse(Integer tid, String term) {
        return courseTeacherMapper.findMyCourse(tid, term);
    }

    @Override
    public List<CourseTeacherInfo> findCourseTeacherInfo(Map<String, String> map) {
        Integer tid = null, cid = null;
        Integer tFuzzy = null, cFuzzy = null;
        String tname = null, cname = null;
        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("tname")) {
            tname = map.get("tname");
        }
        if (map.containsKey("cname")) {
            cname = map.get("cname");
        }
        if (map.containsKey("tFuzzy")) {
            tFuzzy = (map.get("tFuzzy").equals("true")) ? 1 : 0;
        }
        if (map.containsKey("cFuzzy")) {
            cFuzzy = (map.get("cFuzzy").equals("true")) ? 1 : 0;
        }
        System.out.println("Course-teacher fuzzy search map: " + map);
        System.out.println(courseTeacherMapper.findCourseTeacherInfo(tid, tname, tFuzzy, cid, cname, cFuzzy));
        return courseTeacherMapper.findCourseTeacherInfo(tid, tname, tFuzzy, cid, cname, cFuzzy);
    }

    @Override
    public List<CourseTeacher> findBySearch(Integer cid, Integer tid, String term) {
        return courseTeacherMapper.findBySearch(cid, tid, term);
    }

    @Override
    public List<CourseTeacher> findBySearch(Map<String, String> map) {
        Integer cid = null;
        Integer tid = null;
        String  term = null;

        if (map.containsKey("term")) {
            term = map.get("term");
        }

        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            }
            catch (Exception e) {
            }
        }

        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            }
            catch (Exception e) {
            }
        }
        System.out.println("Course offering search map: " + map);
        return courseTeacherMapper.findBySearch(cid, tid, term);
    }

    @Override
    public boolean deleteById(CourseTeacher courseTeacher) {
        return courseTeacherMapper.deleteById(courseTeacher);
    }
}

