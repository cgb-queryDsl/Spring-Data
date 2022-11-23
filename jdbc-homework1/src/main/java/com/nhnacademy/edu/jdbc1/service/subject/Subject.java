package com.nhnacademy.edu.jdbc1.service.subject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
public class Subject {
    @Setter
    private long id;
    private String name;
    private Timestamp createdAt;

    public Subject(long id, String name, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Subject(String name, Timestamp createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
