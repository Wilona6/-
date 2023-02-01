package com.code.classsystem.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author coder
 * @since 2022-04-22
 */
@Data
public class TestSituation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 课程id
     */
    private String courseId;
    /**
     * 试卷url
     */
    private String questionUrl;
    /**
     * 作业url
     */
    private String homeworkUrl;

    private String username;

    private String courseName;

    private String paperId;

    private String homeworkId;

    private String workTitle;


    private String paperName;

    private String resId;
    private String fileName;

    private CourseResource courseResource;
}
