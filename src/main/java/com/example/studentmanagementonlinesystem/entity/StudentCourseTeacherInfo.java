package com.example.studentmanagementonlinesystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("StudentCourseTeacherInfo")
public class StudentCourseTeacherInfo {
    private Integer sctid;
    private Integer sid;
    private Integer cid;
    private Integer tid;
    private String sname;
    private String cname;
    private String tname;
    private Float grade;
    private String term;
}
