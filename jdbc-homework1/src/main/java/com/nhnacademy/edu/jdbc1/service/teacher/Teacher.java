package com.nhnacademy.edu.jdbc1.service.teacher;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class Teacher {
    @Setter
    private Long id;
    private String name;
    private Date createdAt;

    public Teacher(Long id, String name, Date createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Teacher(String name, Date createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
