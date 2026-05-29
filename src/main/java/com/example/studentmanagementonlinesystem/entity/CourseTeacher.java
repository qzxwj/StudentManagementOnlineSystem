package com.example.studentmanagementonlinesystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("CourseTeacher")
public class CourseTeacher {
    private Integer ctid;
    private Integer cid;
    private Integer tid;
    private String term;
}
