package com.nhnacademy.edu.jdbc1.service.course;

import java.util.List;

public interface CourseRepository {
    Course findById(long id);

    List<Course> findAll();

    int insert(Course course);

    int deleteById(long id);
}
