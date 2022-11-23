package com.nhnacademy.edu.jdbc1.service.course;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
public class Course {
    @Setter
    private long id;
    private long teacherId;
    private long subjectId;
    private Timestamp createdAt;

    public Course(long id, long teacherId, long subjectId, Timestamp createdAt) {
        this.id = id;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.createdAt = createdAt;
    }

    public Course(long teacherId, long subjectId, Timestamp createdAt) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.createdAt = createdAt;
    }
}
