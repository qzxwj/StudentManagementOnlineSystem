package com.example.studentmanagementonlinesystem.controller;

import com.example.studentmanagementonlinesystem.entity.CourseTeacherInfo;
import com.example.studentmanagementonlinesystem.entity.StudentCourseTeacher;
import com.example.studentmanagementonlinesystem.entity.StudentCourseTeacherInfo;
import com.example.studentmanagementonlinesystem.service.StudentCourseTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/SCT")
public class StudentCourseTeacherController {
    @Autowired
    private StudentCourseTeacherService sctService;

    @PostMapping("/save")
    public String save(@RequestBody StudentCourseTeacher studentCourseTeacher) {
        if (sctService.isSCTExist(studentCourseTeacher)) {
            return "Duplicate course selection is not allowed";
        }
        System.out.println("Saving SCT record: " + studentCourseTeacher);
        return sctService.save(studentCourseTeacher) ? "Course selected successfully" : "Course selection failed. Contact the administrator";
    }

    @GetMapping("/findBySid/{sid}/{term}")
    public List<CourseTeacherInfo> findBySid(@PathVariable Integer sid, @PathVariable String term) {
        return sctService.findBySid(sid, term);
    }

    @GetMapping("/findAllTerm")
    public List<String> findAllTerm() {
        return sctService.findAllTerm();
    }

    @PostMapping("/deleteBySCT")
    public boolean deleteBySCT(@RequestBody StudentCourseTeacher studentCourseTeacher) {
        System.out.println("Deleting SCT record: " + studentCourseTeacher);
        return sctService.deleteBySCT(studentCourseTeacher);
    }

    @PostMapping("/findBySearch")
    public List<StudentCourseTeacherInfo> findBySearch(@RequestBody Map<String, String> map) {
        return sctService.findBySearch(map);
    }

    @GetMapping("/findById/{sid}/{cid}/{tid}/{term}")
    public StudentCourseTeacherInfo findById(@PathVariable Integer sid,
                            @PathVariable Integer cid,
                            @PathVariable Integer tid,
                            @PathVariable String term) {
        return sctService.findByIdWithTerm(sid, cid, tid, term);
    }

    @GetMapping("/updateById/{sid}/{cid}/{tid}/{term}/{grade}")
    public boolean updateById(@PathVariable Integer sid,
                              @PathVariable Integer cid,
                              @PathVariable Integer tid,
                              @PathVariable String term,
                              @PathVariable Integer grade) {
        return sctService.updateById(sid, cid, tid, term, grade);
    }

    @GetMapping("/deleteById/{sid}/{cid}/{tid}/{term}")
    public boolean deleteById(@PathVariable Integer sid,
                              @PathVariable Integer cid,
                              @PathVariable Integer tid,
                              @PathVariable String term) {
        return sctService.deleteById(sid, cid, tid, term);
    }
}
