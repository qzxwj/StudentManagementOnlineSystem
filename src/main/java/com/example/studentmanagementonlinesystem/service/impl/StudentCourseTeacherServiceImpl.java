package com.example.studentmanagementonlinesystem.service.impl;

import com.example.studentmanagementonlinesystem.entity.CourseTeacherInfo;
import com.example.studentmanagementonlinesystem.entity.StudentCourseTeacher;
import com.example.studentmanagementonlinesystem.entity.StudentCourseTeacherInfo;
import com.example.studentmanagementonlinesystem.mapper.StudentCourseTeacherMapper;
import com.example.studentmanagementonlinesystem.service.StudentCourseTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentCourseTeacherServiceImpl implements StudentCourseTeacherService {
    @Autowired
    private StudentCourseTeacherMapper studentCourseTeacherMapper;

    @Override
    public List<CourseTeacherInfo> findBySid(Integer sid, String term) {
        return studentCourseTeacherMapper.findByStudentId(sid, term);
    }

    @Override
    public List<String> findAllTerm() {
        return studentCourseTeacherMapper.findAllTerm();
    }

    @Override
    public boolean isSCTExist(StudentCourseTeacher studentCourseTeacher) {
        return studentCourseTeacherMapper.findBySCT(studentCourseTeacher).size() != 0;
    }

    @Override
    public boolean save(StudentCourseTeacher studentCourseTeacher) {
        return studentCourseTeacherMapper.insert(studentCourseTeacher);
    }

    @Override
    public boolean deleteBySCT(StudentCourseTeacher studentCourseTeacher) {
        return studentCourseTeacherMapper.deleteBySCT(studentCourseTeacher);
    }

    @Override
    public boolean deleteById(Integer sid, Integer cid, Integer tid, String  term) {
        StudentCourseTeacher studentCourseTeacher = new StudentCourseTeacher();
        studentCourseTeacher.setSid(sid);
        studentCourseTeacher.setCid(cid);
        studentCourseTeacher.setTid(tid);
        studentCourseTeacher.setTerm(term);
        return studentCourseTeacherMapper.deleteBySCT(studentCourseTeacher);
    }

    @Override
    public StudentCourseTeacherInfo findByIdWithTerm(Integer sid, Integer cid, Integer tid, String term) {
        return studentCourseTeacherMapper.findBySearch(
                sid, null, 0,
                cid, null, 0,
                tid, null, 0,
                null, null, term).get(0);
    }

    @Override
    public boolean updateById(Integer sid, Integer cid, Integer tid, String term, Integer grade) {
        return studentCourseTeacherMapper.updateById(sid, cid, tid, term, grade);
    }

    @Override
    public List<StudentCourseTeacherInfo> findBySearch(Map<String, String> map) {
        Integer sid = null, cid = null, tid = null;
        String sname = null, cname = null, tname = null;
        Integer sFuzzy = null, cFuzzy = null, tFuzzy = null;
        Integer lowBound = null, highBound = null;

        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("sid")) {
            try {
                sid = Integer.parseInt(map.get("sid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("sname")) {
            sname = map.get("sname");
        }
        if (map.containsKey("tname")) {
            tname = map.get("tname");
        }
        if (map.containsKey("cname")) {
            cname = map.get("cname");
        }
        if (map.containsKey("sFuzzy")) {
            sFuzzy = map.get("sFuzzy").equals("true") ? 1 : 0;
        }
        if (map.containsKey("tFuzzy")) {
            tFuzzy = map.get("tFuzzy").equals("true") ? 1 : 0;
        }
        if (map.containsKey("cFuzzy")) {
            cFuzzy = map.get("cFuzzy").equals("true") ? 1 : 0;
        }
        if (map.containsKey("lowBound")) {
            try {
                lowBound = Integer.parseInt(map.get("lowBound"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("highBound")) {
            try {
                highBound = Integer.valueOf(map.get("highBound"));
            }
            catch (Exception e) {
            }
        }

        System.out.println("SCT search map: " + map);
        return studentCourseTeacherMapper.findBySearch(
                sid, sname, sFuzzy,
                cid, cname, cFuzzy,
                tid, tname, tFuzzy,
                lowBound, highBound, null);
    }
}
