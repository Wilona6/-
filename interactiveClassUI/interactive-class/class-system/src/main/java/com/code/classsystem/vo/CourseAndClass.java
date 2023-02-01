package com.code.classsystem.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class CourseAndClass implements Serializable {
    private String courseId;
    private String classId;
    private String courseName;
    private String className;
    private Integer classNum;
    private List<String> courseIds;
}
