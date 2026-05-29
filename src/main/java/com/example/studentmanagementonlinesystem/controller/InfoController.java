package com.example.studentmanagementonlinesystem.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
@CrossOrigin("*")
public class InfoController {
    private final String CURRENT_TERM = "202601";
    private final boolean FORBID_COURSE_SELECTION = false;

    @RequestMapping("/getCurrentTerm")
    public String getCurrentTerm() {
        return CURRENT_TERM;
    }

    @RequestMapping("/getForbidCourseSelection")
    public boolean getForbidCourseSelection() {
        return FORBID_COURSE_SELECTION;
    }
}
