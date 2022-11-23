package com.nhnacademy.edu.jdbc1.service.subject;

import java.util.List;

public interface SubjectRepository {
    Subject findById(long id);

    List<Subject> findAll();

    int insert(Subject subject);

    void update(long id, String subjectName);
}
