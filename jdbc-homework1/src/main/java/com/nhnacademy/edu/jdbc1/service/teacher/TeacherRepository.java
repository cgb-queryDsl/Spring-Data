package com.nhnacademy.edu.jdbc1.service.teacher;

import java.util.List;

public interface TeacherRepository {
    Teacher findById(long id);

    List<Teacher> findAll();

    int insert(Teacher teacher);

    void update(long id, String teacherName);
}
