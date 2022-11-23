package com.nhnacademy.edu.jdbc1.service.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private long courseId;
    private String teacherName;
    private String subjectName;
    private Timestamp createdAt;

    @Override
    public String toString() {
        return "CourseResponse{" +
                "courseId=" + courseId +
                ", teacherName='" + teacherName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
