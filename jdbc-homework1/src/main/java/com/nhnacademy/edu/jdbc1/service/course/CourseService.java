package com.nhnacademy.edu.jdbc1.service.course;

import java.util.List;

public interface CourseService {
    List<CourseResponse> findAllCourses();

    CourseResponse findCourseById(long courseId);

    void createCourse(String teacherName, String subjectName);

    void deleteCourse(long courseId);

    void update(long courseId, String teacherName, String subjectName);
}
