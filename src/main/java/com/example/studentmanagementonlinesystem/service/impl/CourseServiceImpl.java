package com.example.studentmanagementonlinesystem.service.impl;

import com.example.studentmanagementonlinesystem.entity.Course;
import com.example.studentmanagementonlinesystem.mapper.CourseMapper;
import com.example.studentmanagementonlinesystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findBySearch(Map<String, String> map) {
        Integer cid = null;
        Integer lowBound = null;
        Integer highBound = null;
        Integer fuzzy = 0;
        String cname = null;

        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("lowBound")) {
            try {
                lowBound = Integer.parseInt(map.get("lowBound"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("highBound")) {
            try {
                highBound = Integer.valueOf(map.get("highBound"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("cname")) {
            cname = map.get("cname");
        }
        if (map.containsKey("fuzzy")) {
            fuzzy = (map.get("fuzzy").equals("true")) ? 1 : 0;
        }
        System.out.println("Course search map: " + map);
        System.out.println("Course search params: cid=" + cid + ", cname=" + cname + ", fuzzy=" + fuzzy
                + ", lowBound=" + lowBound + ", highBound=" + highBound);
        return courseMapper.findBySearch(cid, cname, fuzzy, lowBound, highBound);
    }

    @Override
    public List<Course> findBySearch(Integer cid) {
        return courseMapper.findBySearch(cid, null, 0, null, null);
    }

    @Override
    public List<Course> findById(Integer cid) {
        HashMap<String, String> map = new HashMap<>();
        if (cid != null)
            map.put("cid", String.valueOf(cid));
        return findBySearch(map);
    }

    @Override
    public boolean updateById(Course course) {
        return courseMapper.updateById(course);
    }

    @Override
    public boolean insertCourse(Course course) {
        return courseMapper.insertCourse(course);
    }

    @Override
    public boolean deleteById(Integer cid) {
        return courseMapper.deleteById(cid);
    }
}
