package com.example.studentmanagementonlinesystem.controller;

import com.example.studentmanagementonlinesystem.entity.Course;
import com.example.studentmanagementonlinesystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/findBySearch")
    public List<Course> findBySearch(@RequestBody Map<String, String> map) {
        return courseService.findBySearch(map);
    }

    @GetMapping("/findById/{cid}")
    public List<Course> findById(@PathVariable Integer cid) {
        return courseService.findBySearch(cid);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Course course) {
        System.out.println(course);
        return courseService.insertCourse(course);
    }

    @GetMapping("/deleteById/{cid}")
    public boolean deleteById(@PathVariable Integer cid) {
        System.out.println("Deleting course by cid: " + cid);
        return courseService.deleteById(cid);
    }

    @PostMapping("/updateCourse")
    public boolean updateCourse(@RequestBody Course course) {
        System.out.println("Updating course: " + course);
        return courseService.updateById(course);
    }

}
