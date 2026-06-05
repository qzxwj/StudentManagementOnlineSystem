package com.example.studentmanagementonlinesystem.controller;

import com.example.studentmanagementonlinesystem.dto.RegisterResponse;
import com.example.studentmanagementonlinesystem.entity.Teacher;
import com.example.studentmanagementonlinesystem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/addTeacher")
    public boolean addTeacher(@RequestBody Teacher teacher) {
        return teacherService.save(teacher);
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody Teacher teacher) {
        if (teacher == null || isBlank(teacher.getTname()) || isBlank(teacher.getPassword())) {
            return new RegisterResponse(null, null, "teacher", "Teacher name and password are required");
        }

        Teacher registeredTeacher = teacherService.register(teacher);
        if (registeredTeacher == null || registeredTeacher.getTid() == null) {
            return new RegisterResponse(null, teacher.getTname(), "teacher", "Teacher registration failed");
        }

        return new RegisterResponse(
                registeredTeacher.getTid(),
                registeredTeacher.getTname(),
                "teacher",
                "Teacher registration successful"
        );
    }

    @PostMapping("/login")
    public boolean login(@RequestBody Teacher teacher) {
        System.out.println("Validating teacher login: " + teacher);
        Teacher t = teacherService.findById(teacher.getTid());
        System.out.println("Teacher record from database: " + t);
        if (t == null || !t.getPassword().equals(teacher.getPassword())) {
            return false;
        }
        else {
            return true;
        }
    }

    @GetMapping("/findById/{tid}")
    public Teacher findById(@PathVariable("tid") Integer tid) {
        System.out.println("Finding teacher by id: " + tid);
        return teacherService.findById(tid);
    }

    @PostMapping("/findBySearch")
    public List<Teacher> findBySearch(@RequestBody Map<String, String> map) {
        return teacherService.findBySearch(map);
    }

    @GetMapping("/deleteById/{tid}")
    public boolean deleteById(@PathVariable("tid") int tid) {
        System.out.println("Deleting teacher by tid: " + tid);
        return teacherService.deleteById(tid);
    }

    @PostMapping("/updateTeacher")
    public boolean updateTeacher(@RequestBody Teacher teacher) {
        System.out.println("Updating teacher: " + teacher);
        return teacherService.updateById(teacher);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
