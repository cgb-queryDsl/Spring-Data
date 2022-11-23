package com.nhnacademy.edu.jdbc1.service.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegisterRequest {
    private String teacherName;
    private String subjectName;
}
